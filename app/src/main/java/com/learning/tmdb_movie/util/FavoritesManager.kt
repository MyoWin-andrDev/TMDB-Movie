package com.learning.tmdb_movie.util

import android.content.Context
import com.learning.tmdb_movie.service.data_store.TMDB_MOVIE_DATASTORE
import kotlinx.coroutines.flow.first

class FavoritesManager(private val context: Context) {

    suspend fun toggleFavorite(movieId: Int): Set<Int> {
        val favorites = TMDB_MOVIE_DATASTORE.observeFavorites(context).first().toMutableSet()
        if (favorites.contains(movieId)) {
            favorites.remove(movieId)
        } else {
            favorites.add(movieId)
        }
        TMDB_MOVIE_DATASTORE.setFavorites(context, favorites)
        return favorites
    }

    suspend fun getFavorites(): Set<Int> =
        TMDB_MOVIE_DATASTORE.observeFavorites(context).first()
}
