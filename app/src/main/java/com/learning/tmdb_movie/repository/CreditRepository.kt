package com.learning.tmdb_movie.repository

import com.learning.tmdb_movie.util.safeApiCall
import com.learning.tmdb_movie.model.dto.response.credits.CastItem
import com.learning.tmdb_movie.service.network.CreditAPIService
import com.learning.tmdb_movie.service.network.RetrofitInstance

class CreditRepository {
    val apiService : CreditAPIService by lazy { RetrofitInstance.getInstance().create(
        CreditAPIService::class.java) }

    suspend fun getMovieCredit(movieId : Int) : Result<List<CastItem>> = safeApiCall (
        apiCall = { apiService.getMovieCredit(id = movieId)},
        onSuccess = {it.cast!!.filterNotNull()}
    )
}