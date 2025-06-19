package com.learning.tmdb_movie.repository

import com.learning.tmdb_movie.util.safeApiCall
import com.learning.tmdb_movie.model.dto.response.movie.MovieItemModel
import com.learning.tmdb_movie.service.network.MovieAPIService
import com.learning.tmdb_movie.service.network.RetrofitInstance

class MovieRepository {
    private val apiService: MovieAPIService by lazy {
        RetrofitInstance.getInstance().create(MovieAPIService::class.java)
    }

    suspend fun getPopularList(): Result<List<MovieItemModel>> = safeApiCall(
        apiCall = { apiService.getPopularList() },
        onSuccess = { it.results!!.filterNotNull() }
    )

    suspend fun getNowPlayingList(): Result<List<MovieItemModel>> = safeApiCall(
        apiCall = { apiService.getNowPlayingList() },
        onSuccess = { it.results!!.filterNotNull() }
    )

    suspend fun getUpComingList(): Result<List<MovieItemModel>> = safeApiCall(
        apiCall = { apiService.getUpComingList() },
        onSuccess = { it.results!!.filterNotNull() }
    )
}