package com.learning.tmdb_movie.service.network.reponse

import com.learning.tmdb_movie.Util.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    fun getInstance() : Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}