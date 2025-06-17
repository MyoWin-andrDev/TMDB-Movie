package com.learning.tmdb_movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arrow.core.Either
import com.learning.tmdb_movie.model.Detail.DetailResponse
import com.learning.tmdb_movie.repository.DetailRepository

class DetailViewModel : ViewModel() {
    private val detailRepository = DetailRepository()

    private val _detailMovie = MutableLiveData<Either<String,DetailResponse>>()
    val detailMovie : LiveData<Either<String,DetailResponse>> = _detailMovie

    suspend fun getDetailMovie(id : Int){
        detailRepository.getMovieDetail(id)
            .onSuccess { movieDetail ->
                _detailMovie.postValue(Either.Right(movieDetail))
            }
            .onFailure { throwable ->
                _detailMovie.postValue(Either.Left(throwable.toString()))
            }
    }
}