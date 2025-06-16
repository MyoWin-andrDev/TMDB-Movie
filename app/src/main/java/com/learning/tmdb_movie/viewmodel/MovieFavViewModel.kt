package com.learning.tmdb_movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arrow.core.Either
import com.learning.tmdb_movie.model.MovieFavourite.MovieFavModel
import com.learning.tmdb_movie.repository.MovieFavRepository

class MovieFavViewModel : ViewModel() {
    val movieFavRepo = MovieFavRepository()

    private val _movieFavList = MutableLiveData<Either<String, List<MovieFavModel>>>()
    val movieFavList : LiveData<Either<String, List<MovieFavModel>>> = _movieFavList

    suspend fun setFavourite(movieFav : MovieFavModel) = try {
        movieFavRepo.setFavourite(movieFav)
            .onSuccess {  }
    }
}