package com.learning.tmdb_movie.repository

import com.google.firebase.firestore.ListenerRegistration
import com.learning.tmdb_movie.Util.ADD_MSG
import com.learning.tmdb_movie.Util.FIRE_STORE_ERROR
import com.learning.tmdb_movie.Util.INIT_LOAD_FAILED
import com.learning.tmdb_movie.Util.REMOVE_MSG
import com.learning.tmdb_movie.model.Favourite.FavouriteResponse
import com.learning.tmdb_movie.service.database.FireStoreInstance
import kotlinx.coroutines.tasks.await

class FavouriteRepository() {
    private val favFireStore = FireStoreInstance.getFavCollection()

    suspend fun toggleFavorite(movie: FavouriteResponse): Result<String> = try {
        val docRef = favFireStore.document(movie.movieId.toString())
        val exists = docRef.get().await().exists()

        if (exists) {
            docRef.delete().await()
            Result.success(REMOVE_MSG)
        } else {
            docRef.set(movie).await()
            Result.success(ADD_MSG)
        }
    } catch (e: Exception) {
        Result.failure(Exception("Failed to toggle favorite: ${e.message}"))
    }

    fun getFavouritesLiveData(onUpdate: (Result<List<FavouriteResponse>>) -> Unit): ListenerRegistration {
        return favFireStore.addSnapshotListener { snapshot, error ->
            if (error != null) {
                onUpdate(Result.failure(Exception(error.message ?: FIRE_STORE_ERROR)))
                return@addSnapshotListener
            }
            val favs = snapshot?.toObjects(FavouriteResponse::class.java) ?: emptyList()
            onUpdate(Result.success(favs))
        }
    }

    suspend fun getInitialFavourites(): Result<List<FavouriteResponse>> = try {
        val snapshot = favFireStore.get().await()
        Result.success(snapshot.toObjects(FavouriteResponse::class.java))
    } catch (e: Exception) {
        Result.failure(Exception(INIT_LOAD_FAILED + e.message))
    }
}