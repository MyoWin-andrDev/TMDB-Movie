package com.learning.tmdb_movie.repository

import com.learning.tmdb_movie.Util.getErrorMessage
import com.learning.tmdb_movie.model.CustomMovieModel
import com.learning.tmdb_movie.service.network.reponse.APIService
import com.learning.tmdb_movie.service.network.reponse.RetrofitInstance

class MovieRepository {
    private val apiService: APIService by lazy {
        RetrofitInstance.getInstance().create(APIService::class.java)
    }

    suspend fun getPopularList(): Result<List<CustomMovieModel>> = try {
        var popularList = ArrayList<CustomMovieModel>()
        val response = apiService.getPopularList()
        when {
            response.isSuccessful && response.body() != null -> {
                popularList.addAll(response.body()!!.results!!.filterNotNull())
                Result.success(popularList)
            }

            else -> Result.failure(Exception(response.getErrorMessage()))
        }
    } catch (e: Exception) {
            Result.failure(e)
    }

    suspend fun getNowPlayingList() : Result<List<CustomMovieModel>> = try {
        val nowPlayingList = ArrayList<CustomMovieModel>()
        val response = apiService.getNowPlayingList()
        when {
            response.isSuccessful && response.body() != null -> {
                nowPlayingList.addAll(response.body()!!.results!!.filterNotNull())
                Result.success(nowPlayingList)
            }
            else -> Result.failure(Exception(response.getErrorMessage()))
        }
    } catch (e : Exception){
        Result.failure(e)
    }

    suspend fun getUpComingList() : Result<List<CustomMovieModel>> = try {
        val upComingList = ArrayList<CustomMovieModel>()
        val response = apiService.getUpComingList()
        when {
            response.isSuccessful && response.body() != null -> {
                upComingList.addAll(response.body()!!.results!!.filterNotNull())
                Result.success(upComingList)
            }
            else -> Result.failure(Exception(response.getErrorMessage()))
        }
    } catch (e : Exception){
        Result.failure(e)
    }

}