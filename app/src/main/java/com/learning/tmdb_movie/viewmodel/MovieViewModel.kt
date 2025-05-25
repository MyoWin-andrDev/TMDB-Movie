package com.learning.tmdb_movie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learning.tmdb_movie.model.CustomMovieModel
import com.learning.tmdb_movie.model.NowPlayingResponse
import com.learning.tmdb_movie.model.NowPlayingResultsModel
import com.learning.tmdb_movie.model.PopularResultsModel
import com.learning.tmdb_movie.model.UpComingResultModel
import com.learning.tmdb_movie.model.UpcomingResponse
import com.learning.tmdb_movie.repository.MovieRepository

class MovieViewModel : ViewModel() {
    val movieRepository = MovieRepository()

    private var _popularList = MutableLiveData<List<CustomMovieModel>>()
    val popularList : LiveData<List<CustomMovieModel>> = _popularList

    private var _nowPlayingList = MutableLiveData<List<CustomMovieModel>>()
    val nowPlayingList : LiveData<List<CustomMovieModel>> = _nowPlayingList

    private var _upComingList = MutableLiveData<List<CustomMovieModel>>()
    val upComingList : LiveData<List<CustomMovieModel>> = _upComingList

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