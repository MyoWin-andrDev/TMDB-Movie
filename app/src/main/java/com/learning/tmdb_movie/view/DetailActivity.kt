package com.learning.tmdb_movie.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.learning.tmdb_movie.Util.IMAGE_BASE_URL
import com.learning.tmdb_movie.Util.INTENT_ID
import com.learning.tmdb_movie.Util.formatDate
import com.learning.tmdb_movie.Util.showToast
import com.learning.tmdb_movie.adapter.CastAdapter
import com.learning.tmdb_movie.databinding.ActivityDetailBinding
import com.learning.tmdb_movie.model.Detail.CastItem
import com.learning.tmdb_movie.model.Detail.DetailResponse
import com.learning.tmdb_movie.model.Favourite.FavouriteResponse
import com.learning.tmdb_movie.viewmodel.CreditViewModel
import com.learning.tmdb_movie.viewmodel.DetailViewModel
import com.learning.tmdb_movie.viewmodel.FavouriteViewModel
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private val creditViewModel: CreditViewModel by viewModels()
    private val favouriteViewModel: FavouriteViewModel by viewModels()

    private val movieId by lazy { intent.getIntExtra(INTENT_ID, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeBinding()
        setupObservers()
        loadData()
    }

    private fun initializeBinding() {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupFavoriteToggle()
    }

    private fun setupFavoriteToggle() {
        binding.tbFavorite.setOnCheckedChangeListener(null)
        binding.tbFavorite.isChecked = false
        binding.tbFavorite.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                detailViewModel.detailMovie.value?.fold(
                    { /* Error already handled */ },
                    { _ -> toggleFavoriteStatus(isChecked) }
                )
            }
        }
    }

    private fun toggleFavoriteStatus(isChecked: Boolean) {
        favouriteViewModel.toggleFavourite(
            FavouriteResponse(
                movieId = movieId,
                isFavourite = isChecked
            )
        )
    }

    private fun loadData() {
        lifecycleScope.launchWhenStarted {
            detailViewModel.getDetailMovie(movieId)
            creditViewModel.getCreditMovie(movieId)
        }
    }

    private fun setupObservers() {
        observeMovieDetails()
        observeCastList()
        observeFavorites()
    }

    private fun observeMovieDetails() {
        detailViewModel.detailMovie.observe(this) { response ->
            response.fold(
                ::showToast,
                ::bindMovieDetails
            )
        }
    }

    private fun observeCastList() {
        creditViewModel.castList.observe(this) { response ->
            response.fold(
                ::showToast,
                ::setupCastAdapter
            )
        }
    }

    private fun observeFavorites() {
        favouriteViewModel.favouriteList.observe(this) { response ->
            response.fold(
                ::showToast,
                ::updateFavoriteStatus
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindMovieDetails(details: DetailResponse) {
        with(binding) {
            loadBackdropImage(details.backdropPath)
            tvMovieTitle.text = details.title
            tvReleaseInfo.text = formatReleaseInfo(details)
            tvRating.text = "${details.voteAverage}%"
            tvVotes.text = "${details.voteCount} votes"
            tvLanguage.text = details.spokenLanguages?.firstOrNull()?.name
            tvDescription.text = details.overview
        }
    }

    private fun loadBackdropImage(backdropPath: String?) {
        Glide.with(this)
            .load(IMAGE_BASE_URL + backdropPath)
            .into(binding.ivBackdrop)
    }

    private fun formatReleaseInfo(details: DetailResponse): String {
        return "${details.productionCountries?.firstOrNull()?.iso31661} | ${
            formatDate(details.releaseDate.orEmpty())
        }"
    }

    private fun setupCastAdapter(castList: List<CastItem>) {
        binding.rvCast.rvMain.adapter = CastAdapter(castList)
    }

    private fun updateFavoriteStatus(favList: List<FavouriteResponse>) {
        binding.tbFavorite.isChecked = favList.any { it.movieId == movieId }
    }
}