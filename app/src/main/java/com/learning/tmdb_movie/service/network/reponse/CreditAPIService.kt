package com.learning.tmdb_movie.service.network.reponse

import com.learning.tmdb_movie.Util.ACCEPT
import com.learning.tmdb_movie.Util.AUTHENTICATION
import com.learning.tmdb_movie.model.MovieDetail.CreditsResponse
import com.learning.tmdb_movie.model.MovieDetail.DetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CreditAPIService{
    @GET("{movie_id}/credits")
    suspend fun getMovieCredit(
        @Header("Authorization")
        auth : String = AUTHENTICATION,
        @Header("accept")
        accept : String = ACCEPT,
        @Path("movie_id")
        id : Int
    ) : Response<CreditsResponse>
}