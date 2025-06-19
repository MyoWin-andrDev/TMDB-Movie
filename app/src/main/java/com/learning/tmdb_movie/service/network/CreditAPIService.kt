package com.learning.tmdb_movie.service.network

import com.learning.tmdb_movie.model.dto.response.credits.CreditsResponse
import com.learning.tmdb_movie.service.APIEndpoints.CREDITS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CreditAPIService {
    @GET("{movie_id}$CREDITS")
    suspend fun getMovieCredit(
        @Path("movie_id")
        id: Int
    ): Response<CreditsResponse>
}