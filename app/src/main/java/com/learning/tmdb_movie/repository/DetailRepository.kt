package com.learning.tmdb_movie.repository

import com.learning.tmdb_movie.Util.safeApiCall
import com.learning.tmdb_movie.model.Detail.DetailResponse
import com.learning.tmdb_movie.service.network.reponse.DetailAPIService
import com.learning.tmdb_movie.service.network.reponse.RetrofitInstance

class DetailRepository(){
    val apiService : DetailAPIService by lazy {
        RetrofitInstance.getInstance().create(DetailAPIService::class.java)
    }

    suspend fun getMovieDetail(movieId : Int) : Result<DetailResponse> = safeApiCall(
        apiCall = { apiService.getMovieDetail(id = movieId) },
        onSuccess = {it}
    )
}