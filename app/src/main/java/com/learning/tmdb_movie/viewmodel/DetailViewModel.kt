package com.learning.tmdb_movie.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.learning.tmdb_movie.model.ui_model.DetailUiModel
import com.learning.tmdb_movie.repository.DetailRepository
import com.learning.tmdb_movie.service.data_store.TMDB_MOVIE_DATASTORE
import com.learning.tmdb_movie.util.FavoritesManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    private val detailRepository = DetailRepository()

    private val _detailMovie = MutableLiveData<Either<String, DetailUiModel>>()
    val detailMovie: LiveData<Either<String, DetailUiModel>> = _detailMovie

    fun getDetailMovie(context: Context, id: Int) = viewModelScope.launch {
        val favorites = TMDB_MOVIE_DATASTORE.observeFavorites(context).first()

        detailRepository.getMovieDetail(id)
            .onSuccess { movieDetail ->
                val isFav = movieDetail.id != null && favorites.contains(movieDetail.id)
                _detailMovie.postValue(Either.Right(DetailUiModel(movieDetail, isFav)))
            }
            .onFailure { throwable ->
                _detailMovie.postValue(Either.Left(throwable.toString()))
            }
    }

    fun onFavClick(context: Context, movieId: Int) = viewModelScope.launch {
        val favoritesManager = FavoritesManager(context)
        val favorites = favoritesManager.toggleFavorite(movieId)
        val isFav = favorites.contains(movieId)

        detailMovie.value?.rightOrNull()?.let { detail ->
            _detailMovie.postValue(
                Either.Right(
                    DetailUiModel(
                        detail = detail.detail,
                        isFavorite = isFav
                    )
                )
            )
        }
    }


    fun <A, B> Either<A, B>.rightOrNull(): B? = when (this) {
        is Either.Right -> this.value
        is Either.Left -> null
    }
}
