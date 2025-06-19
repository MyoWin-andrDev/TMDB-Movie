package com.learning.tmdb_movie.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import arrow.core.Either
import com.bumptech.glide.Glide
import com.learning.tmdb_movie.R
import com.learning.tmdb_movie.adapter.CastAdapter
import com.learning.tmdb_movie.databinding.ActivityDetailBinding
import com.learning.tmdb_movie.model.dto.response.detail.DetailResponse
import com.learning.tmdb_movie.model.ui_model.DetailUiModel
import com.learning.tmdb_movie.util.IMAGE_BASE_URL
import com.learning.tmdb_movie.util.INTENT_ID
import com.learning.tmdb_movie.util.formatDate
import com.learning.tmdb_movie.util.showToast
import com.learning.tmdb_movie.viewmodel.CreditViewModel
import com.learning.tmdb_movie.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private val creditViewModel: CreditViewModel by viewModels()

    private val movieId by lazy { intent.getIntExtra(INTENT_ID, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cvBackArrow.setOnClickListener { finish() }
        setupObservers()
        loadData()
    }

    private fun loadData() {
        detailViewModel.getDetailMovie(this@DetailActivity, movieId)
        creditViewModel.getCreditMovie(movieId)
    }

    private fun setupObservers() {
        observeResult(
            liveData = detailViewModel.detailMovie,
            onSuccess = ::bindMovieDetails
        )
        observeResult(liveData = creditViewModel.castList, onSuccess = {
            binding.rvCast.rvMain.adapter = CastAdapter(it)
        })
    }

    private fun <T> observeResult(
        liveData: LiveData<Either<String, T>>,
        onSuccess: (T) -> Unit
    ) {
        liveData.observe(this) { either ->
            either.fold(
                ifLeft = ::showToast,
                ifRight = onSuccess
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindMovieDetails(details: DetailUiModel) = with(binding) {
        pbLoading.visibility = ViewGroup.INVISIBLE
        csMain.visibility = ViewGroup.VISIBLE

        val colorRes =
            if (details.isFavorite) R.color.red else R.color.grey
        ivFav.imageTintList =
            ContextCompat.getColorStateList(binding.root.context, colorRes)

        details.detail.apply {
            Glide.with(this@DetailActivity)
                .load(IMAGE_BASE_URL + backdropPath)
                .into(binding.ivBackdrop)

            ivFav.setOnClickListener {
                detailViewModel.onFavClick(this@DetailActivity, details.detail.id!!)
            }

            tvMovieTitle.text = title
            tvReleaseInfo.text = formatReleaseInfo(this)
            tvRating.text = "${voteAverage}%"
            tvVotes.text = "${voteCount} votes"
            tvLanguage.text = spokenLanguages?.firstOrNull()?.name
            tvDescription.text = overview
        }
    }

    private fun formatReleaseInfo(details: DetailResponse): String {
        return "${details.productionCountries?.firstOrNull()?.iso31661} | ${
            formatDate(details.releaseDate.orEmpty())
        }"
    }

}