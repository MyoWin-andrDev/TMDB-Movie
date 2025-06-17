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

class FavouriteViewModel() : ViewModel() {

    private val repository = FavouriteRepository()

    private val _favouriteList = MutableLiveData<Either<String, List<FavouriteResponse>>>()
    val favouriteList: LiveData<Either<String, List<FavouriteResponse>>> = _favouriteList

    private var snapshotListener: ListenerRegistration? = null

    init {
        setupRealTimeUpdates()
        loadInitialData()
    }

    private fun setupRealTimeUpdates() {
        snapshotListener?.remove()
        snapshotListener = repository.getFavouritesLiveData { result ->
            result.fold(
                onSuccess = { favs ->
                    _favouriteList.postValue(Either.Right(favs))
                },
                onFailure = { e ->
                    _favouriteList.postValue(Either.Left(e.message ?: REAL_TIME_ERROR))
                }
            )
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            repository.getInitialFavourites()
                .fold(
                    onSuccess = { favs ->
                        _favouriteList.postValue(Either.Right(favs))
                    },
                    onFailure = { e ->
                        _favouriteList.postValue(Either.Left(e.message ?: INIT_LOAD_FAILED))
                    }
                )
        }
    }

    fun toggleFavourite(movieFav: FavouriteResponse) {
        viewModelScope.launch {
            repository.toggleFavorite(movieFav)
                .fold(
                    onSuccess = { /* Listener will handle update */ },
                    onFailure = { e ->
                        _favouriteList.postValue(Either.Left(TOGGLE_ERROR + e.message))
                    }
                )
        }
    }

    override fun onCleared() {
        super.onCleared()
        snapshotListener?.remove()
    }
}