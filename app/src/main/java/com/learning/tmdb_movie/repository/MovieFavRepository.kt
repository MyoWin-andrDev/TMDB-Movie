package com.learning.tmdb_movie.repository

import com.learning.tmdb_movie.Util.ADD_MSG
import com.learning.tmdb_movie.Util.FAILED_MSG
import com.learning.tmdb_movie.model.MovieFavourite.MovieFavModel
import com.learning.tmdb_movie.service.database.FireStoreInstance
import kotlinx.coroutines.tasks.await

class MovieFavRepository {
    val favFireStore = FireStoreInstance.getFavCollection()

    suspend fun setFavourite(movieFav : MovieFavModel) : Result<String> = try {
        favFireStore.document(movieFav.movieId.toString()).set(movieFav).await()
        Result.success(ADD_MSG)
    }
    catch (e : Exception){
        Result.failure(Exception(FAILED_MSG + e.message))
    }

    suspend fun getFavourite() : Result<List<MovieFavModel>> = try {
        val favList = favFireStore.get().await()
        Result.success(favList.toObjects(MovieFavModel::class.java))
    }
    catch (e : Exception){
        Result.failure(Exception(FAILED_MSG + e.message))
    }
}