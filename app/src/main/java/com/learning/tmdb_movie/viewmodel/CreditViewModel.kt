package com.learning.tmdb_movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.learning.tmdb_movie.model.dto.response.credits.CastItem
import com.learning.tmdb_movie.repository.CreditRepository
import kotlinx.coroutines.launch

class CreditViewModel : ViewModel() {
    val creditRepo = CreditRepository()

    private val _castList = MutableLiveData<Either<String, List<CastItem>>>()
    val castList: LiveData<Either<String, List<CastItem>>> = _castList

    fun getCreditMovie(movieId: Int) = viewModelScope.launch {
        creditRepo.getMovieCredit(movieId)
            .onSuccess { castList ->
                _castList.postValue(Either.Right(castList))
            }
            .onFailure { throwable ->
                _castList.postValue(Either.Left(throwable.toString()))
            }
    }
}