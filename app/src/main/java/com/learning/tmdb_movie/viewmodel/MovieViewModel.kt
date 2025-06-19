package com.learning.tmdb_movie.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learning.tmdb_movie.model.dto.response.movie.MovieItemModel
import com.learning.tmdb_movie.repository.MovieRepository
import com.learning.tmdb_movie.service.data_store.TMDB_MOVIE_DATASTORE
import com.learning.tmdb_movie.util.FavoritesManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {

    private val repository = MovieRepository()

    private val _popularList = MutableLiveData<List<MovieItemModel>>()
    val popularList: LiveData<List<MovieItemModel>> get() = _popularList

    private val _nowPlayingList = MutableLiveData<List<MovieItemModel>>()
    val nowPlayingList: LiveData<List<MovieItemModel>> get() = _nowPlayingList

    private val _upComingList = MutableLiveData<List<MovieItemModel>>()
    val upComingList: LiveData<List<MovieItemModel>> get() = _upComingList

    private val _errorStatus = MutableLiveData<String>()
    val errorStatus: LiveData<String> get() = _errorStatus

    fun initData(context: Context) = viewModelScope.launch {
        val favorites = TMDB_MOVIE_DATASTORE.observeFavorites(context).first()
        getPopularList(favorites)
        getNowPlayingList(favorites)
        getUpComingList(favorites)
    }

    private fun getPopularList(favorites: Set<Int>) = fetchMovies(
        fetcher = { repository.getPopularList() },
        target = _popularList,
        favorites = favorites
    )

    private fun getNowPlayingList(favorites: Set<Int>) = fetchMovies(
        fetcher = { repository.getNowPlayingList() },
        target = _nowPlayingList,
        favorites = favorites
    )

    private fun getUpComingList(favorites: Set<Int>) = fetchMovies(
        fetcher = { repository.getUpComingList() },
        target = _upComingList,
        favorites = favorites
    )

    private fun fetchMovies(
        fetcher: suspend () -> Result<List<MovieItemModel>>,
        target: MutableLiveData<List<MovieItemModel>>,
        favorites: Set<Int>
    ) = viewModelScope.launch {
        fetcher().onSuccess { movies ->
            val updatedMovies = movies.map { it.copy(isFavorite = it.id in favorites) }
            target.postValue(updatedMovies)
        }.onFailure { error ->
            _errorStatus.postValue(error.message)
        }
    }

    fun onFavClick(context: Context, movieId: Int) = viewModelScope.launch {
        val favoritesManager = FavoritesManager(context)
        val favorites = favoritesManager.toggleFavorite(movieId)

        updateFavoriteInList(_popularList, favorites)
        updateFavoriteInList(_nowPlayingList, favorites)
        updateFavoriteInList(_upComingList, favorites)
    }


    private fun updateFavoriteInList(
        liveData: MutableLiveData<List<MovieItemModel>>,
        favorites: Set<Int>
    ) {
        val updated = liveData.value?.map { it.copy(isFavorite = it.id in favorites) }
        liveData.postValue(updated)
    }

}
