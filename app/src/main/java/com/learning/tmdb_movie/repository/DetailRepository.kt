package com.learning.tmdb_movie.repository

import com.learning.tmdb_movie.util.safeApiCall
import com.learning.tmdb_movie.model.dto.response.detail.DetailResponse
import com.learning.tmdb_movie.service.network.DetailAPIService
import com.learning.tmdb_movie.service.network.RetrofitInstance

class DetailRepository(){
    val apiService : DetailAPIService by lazy {
        RetrofitInstance.getInstance().create(DetailAPIService::class.java)
    }

    suspend fun getMovieDetail(movieId : Int) : Result<DetailResponse> = safeApiCall(
        apiCall = { apiService.getMovieDetail(id = movieId) },
        onSuccess = {it}
    )
}