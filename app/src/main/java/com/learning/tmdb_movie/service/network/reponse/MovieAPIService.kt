package com.learning.tmdb_movie.service.network.reponse

import com.learning.tmdb_movie.Util.ACCEPT
import com.learning.tmdb_movie.Util.AUTHENTICATION
import com.learning.tmdb_movie.Util.LANGUAGE
import com.learning.tmdb_movie.Util.NOW_PLAYING_ENDPOINT
import com.learning.tmdb_movie.Util.PAGE
import com.learning.tmdb_movie.Util.RECOMMEND_ENDPOINT
import com.learning.tmdb_movie.Util.UPCOMING_ENDPOINT
import com.learning.tmdb_movie.model.Movie.NowPlayingResponse
import com.learning.tmdb_movie.model.Movie.PopularResponse
import com.learning.tmdb_movie.model.Movie.UpcomingResponse
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieAPIService {
    //Recommend Request
    @GET(RECOMMEND_ENDPOINT)
    suspend fun getPopularList (
        @Header("Authorization")
        auth : String = AUTHENTICATION,
        @Header("accept")
        app : String = ACCEPT,
        @Query("language")
        language : String = LANGUAGE,
        @Query("page")
        page : Int = PAGE
    ) : Response<PopularResponse>

    //Now_Playing Request
    @GET(NOW_PLAYING_ENDPOINT)
    suspend fun getNowPlayingList(
        @Header("Authorization")
        auth : String = AUTHENTICATION,
        @Header("accept")
        app : String = ACCEPT,
        @Query("language")
        language : String = LANGUAGE,
        @Query("page")
        page : Int = PAGE
    ) : Response<NowPlayingResponse>

    //UpComing Request
    @GET(UPCOMING_ENDPOINT)
    suspend fun getUpComingList(
        @Header("Authorization")
        auth : String = AUTHENTICATION,
        @Header("accept")
        app : String = ACCEPT,
        @Query("language")
        language : String = LANGUAGE,
        @Query("page")
        page : Int = PAGE
    ) : Response<UpcomingResponse>
}