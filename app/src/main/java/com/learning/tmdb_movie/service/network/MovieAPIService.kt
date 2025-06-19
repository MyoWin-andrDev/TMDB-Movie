package com.learning.tmdb_movie.service.network

import com.learning.tmdb_movie.util.DEFAULT_LANGUAGE
import com.learning.tmdb_movie.util.DEFAULT_PAGE
import com.learning.tmdb_movie.model.dto.response.movie.MovieResponse
import com.learning.tmdb_movie.service.APIEndpoints
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPIService {
    //Recommend Request
    @GET(APIEndpoints.RECOMMEND_ENDPOINT)
    suspend fun getPopularList(
        @Query("language")
        language: String = DEFAULT_LANGUAGE,
        @Query("page")
        page: Int = DEFAULT_PAGE
    ): Response<MovieResponse>

    //Now_Playing Request
    @GET(APIEndpoints.NOW_PLAYING_ENDPOINT)
    suspend fun getNowPlayingList(
        @Query("language")
        language: String = DEFAULT_LANGUAGE,
        @Query("page")
        page: Int = DEFAULT_PAGE
    ): Response<MovieResponse>

    //UpComing Request
    @GET(APIEndpoints.UPCOMING_ENDPOINT)
    suspend fun getUpComingList(
        @Query("language")
        language: String = DEFAULT_LANGUAGE,
        @Query("page")
        page: Int = DEFAULT_PAGE
    ): Response<MovieResponse>
}