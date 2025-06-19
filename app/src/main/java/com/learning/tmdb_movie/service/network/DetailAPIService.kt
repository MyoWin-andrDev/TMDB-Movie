package com.learning.tmdb_movie.service.network

import com.learning.tmdb_movie.model.dto.response.detail.DetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailAPIService {
    @GET("{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id")
        id: Int
    ): Response<DetailResponse>
}