package com.learning.tmdb_movie.repository

import com.learning.tmdb_movie.Util.safeApiCall
import com.learning.tmdb_movie.model.MovieDetail.CreditsResponse
import com.learning.tmdb_movie.service.network.reponse.CreditAPIService
import com.learning.tmdb_movie.service.network.reponse.RetrofitInstance

class CreditRepository {
    val apiService : CreditAPIService by lazy { RetrofitInstance.getInstance().create(
        CreditAPIService::class.java) }

    suspend fun getMovieCredit(movieId : Int) : Result<CreditsResponse> = safeApiCall (
        apiCall = { apiService.getMovieCredit(id = movieId)},
        onSuccess = {it}
    )
}