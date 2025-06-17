package com.learning.tmdb_movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Either
import com.learning.tmdb_movie.model.Favourite.FavouriteResponse
import com.learning.tmdb_movie.repository.FavouriteRepository
import com.google.firebase.firestore.ListenerRegistration
import com.learning.tmdb_movie.Util.INIT_LOAD_FAILED
import com.learning.tmdb_movie.Util.REAL_TIME_ERROR
import com.learning.tmdb_movie.Util.TOGGLE_ERROR
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteViewModel : ViewModel() {

    private val repository = FavouriteRepository()

    private val _favouriteList = MutableLiveData<Either<String, List<FavouriteResponse>>>()
    val favouriteList: LiveData<Either<String, List<FavouriteResponse>>> = _favouriteList

    private var snapshotListener: ListenerRegistration? = null

    init {
        setupRealTimeUpdates()
        loadInitialData()
    }

    override fun onCleared() {
        super.onCleared()
        snapshotListener?.remove()
    }

    fun toggleFavourite(movieFav: FavouriteResponse) {
        viewModelScope.launch {
            repository.toggleFavorite(movieFav)
                .onFailure { e ->
                    postError(TOGGLE_ERROR + e.message)
                }
        }
    }

    private fun setupRealTimeUpdates() {
        snapshotListener?.remove()
        snapshotListener = repository.getFavouritesLiveData { result ->
            result.fold(
                onSuccess = { favs -> postSuccess(favs) },
                onFailure = { e -> postError(e.message ?: REAL_TIME_ERROR) }
            )
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            repository.getInitialFavourites()
                .fold(
                    onSuccess = { favs -> postSuccess(favs) },
                    onFailure = { e -> postError(e.message ?: INIT_LOAD_FAILED) }
                )
        }
    }

    private fun postSuccess(favourites: List<FavouriteResponse>) {
        _favouriteList.postValue(Either.Right(favourites))
    }

    private fun postError(error: String?) {
        _favouriteList.postValue(Either.Left(error.orEmpty()))
    }
}