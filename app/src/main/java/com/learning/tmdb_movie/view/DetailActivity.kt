package com.learning.tmdb_movie.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
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
    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        movieId = intent.getIntExtra(INTENT_ID, 0)
        setupObservers()
        loadData()
    }

    private fun loadData() {
        lifecycleScope.launchWhenStarted {
            detailViewModel.getDetailMovie(movieId)
            creditViewModel.getCreditMovie(movieId)
        }
    }

    private fun setupObservers() {
        detailViewModel.detailMovie.observe(this) { response ->
            response.fold(
                { error -> showToast(error) },
                { details -> setupMovieDetails(details) }
            )
        }

        creditViewModel.castList.observe(this) { response ->
            response.fold(
                { error -> showToast(error) },
                { castList ->
                    binding.rvCast.rvMain.adapter = CastAdapter(castList)
                }
            )
        }

        favouriteViewModel.favouriteList.observe(this) { response ->
            response.fold(
                { error -> showToast(error) },
                { favList ->
                    val isFav = favList.any { it.movieId == movieId }
                    binding.tbFavorite.isChecked = isFav
                }
            )
        }

        binding.tbFavorite.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                detailViewModel.detailMovie.value?.fold(
                    { /* Error already handled */ },
                    { details ->
                        favouriteViewModel.toggleFavourite(
                            FavouriteResponse(
                                movieId = movieId,
                                isFavourite = isChecked
                            )
                        )
                    }
                )
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupMovieDetails(details: DetailResponse) {
        with(binding) {
            Glide.with(this@DetailActivity)
                .load(IMAGE_BASE_URL + details.backdropPath)
                .into(ivBackdrop)

            tvMovieTitle.text = details.title
            tvReleaseInfo.text = "${details.productionCountries?.firstOrNull()?.iso31661} | ${formatDate(details.releaseDate.orEmpty())}"
            tvRating.text = details.voteAverage.toString()
            tvVotes.text = details.voteCount.toString()
            tvLanguage.text = details.spokenLanguages?.firstOrNull()?.name
            tvDescriptionTitle.text = details.overview
        }
    }
}