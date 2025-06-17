package com.learning.tmdb_movie.service.network.reponse

import com.learning.tmdb_movie.Util.ACCEPT
import com.learning.tmdb_movie.Util.AUTHENTICATION
import com.learning.tmdb_movie.model.Detail.DetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface DetailAPIService {
    @GET("{movie_id}")
    suspend fun getMovieDetail(
        @Header("Authorization")
        auth : String = AUTHENTICATION,
        @Header("accept")
        app : String = ACCEPT,
        @Path("movie_id")
        id : Int
    ) : Response<DetailResponse>
}