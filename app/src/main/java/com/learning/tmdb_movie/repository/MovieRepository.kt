package com.learning.tmdb_movie.repository

import com.learning.tmdb_movie.Util.getErrorMessage
import com.learning.tmdb_movie.Util.safeApiCall
import com.learning.tmdb_movie.model.MovieEntityModel
import com.learning.tmdb_movie.service.network.reponse.MovieAPIService
import com.learning.tmdb_movie.service.network.reponse.RetrofitInstance

class MovieRepository {
    private val apiService: MovieAPIService by lazy {
        RetrofitInstance.getInstance().create(MovieAPIService::class.java)
    }

    suspend fun getPopularList(): Result<List<MovieEntityModel>> = safeApiCall(
        apiCall = { apiService.getPopularList() },
        onSuccess = { it.results!!.filterNotNull() }
    )

    suspend fun getNowPlayingList(): Result<List<MovieEntityModel>> = safeApiCall(
        apiCall = { apiService.getNowPlayingList() },
        onSuccess = { it.results!!.filterNotNull() }
    )

    suspend fun getUpComingList(): Result<List<MovieEntityModel>> = safeApiCall(
        apiCall = { apiService.getUpComingList() },
        onSuccess = { it.results!!.filterNotNull() }
    )
}