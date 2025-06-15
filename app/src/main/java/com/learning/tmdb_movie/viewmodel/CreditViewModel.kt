package com.learning.tmdb_movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import arrow.core.Either
import com.learning.tmdb_movie.model.MovieDetail.CreditsResponse
import com.learning.tmdb_movie.repository.CreditRepository

class CreditViewModel : ViewModel() {
    val creditRepo = CreditRepository()

    private val _castList = MutableLiveData<Either<String, CreditsResponse>>()
    val castList : LiveData<Either<String, CreditsResponse>> = _castList

    suspend fun getCreditMovie (movieId : Int) {
        creditRepo.getMovieCredit(movieId)
            .onSuccess { castList ->
                _castList.postValue(Either.Right(castList))
            }
            .onFailure { throwable ->
                _castList.postValue(Either.Left(throwable.toString()))
            }
    }
}