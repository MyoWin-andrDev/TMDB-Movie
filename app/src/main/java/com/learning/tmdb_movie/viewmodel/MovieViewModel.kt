package com.learning.tmdb_movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learning.tmdb_movie.model.MovieEntityModel
import com.learning.tmdb_movie.repository.MovieRepository

class MovieViewModel : ViewModel() {
    val movieRepository = MovieRepository()

    private var _popularList = MutableLiveData<List<MovieEntityModel>>()
    val popularList : LiveData<List<MovieEntityModel>> = _popularList

    private var _nowPlayingList = MutableLiveData<List<MovieEntityModel>>()
    val nowPlayingList : LiveData<List<MovieEntityModel>> = _nowPlayingList

    private var _upComingList = MutableLiveData<List<MovieEntityModel>>()
    val upComingList : LiveData<List<MovieEntityModel>> = _upComingList

    private val _errorStatus = MutableLiveData<String>()
    val errorStatus : LiveData<String> = _errorStatus

    suspend fun getPopularList(){
        movieRepository.getPopularList()
            .onSuccess {
                _popularList.postValue(it)
            }
            .onFailure {
                _errorStatus.postValue(it.message)
            }
    }

    suspend fun getNowPlayingList(){
        movieRepository.getNowPlayingList()
            .onSuccess {
                _nowPlayingList.postValue(it)
            }
            .onFailure {
                _errorStatus.postValue(it.message)
            }
    }

    suspend fun getUpComingList(){
        movieRepository.getUpComingList()
            .onSuccess {
                _upComingList.postValue(it)
            }
            .onFailure {
                _errorStatus.postValue(it.message)
            }
    }
}