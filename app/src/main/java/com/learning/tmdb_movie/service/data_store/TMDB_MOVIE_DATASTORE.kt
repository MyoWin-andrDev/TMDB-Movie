package com.learning.tmdb_movie.service.data_store

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object TMDB_MOVIE_DATASTORE {
    private val Context.dataStore by preferencesDataStore(name = "tmdb_movie_datastore")

    private val IS_FAVORITE = stringSetPreferencesKey("is_favorite")

    suspend fun setFavorites(context: Context, favorites: Set<Int>) {
        context.dataStore.edit { prefs ->
            prefs[IS_FAVORITE] = favorites.map { it.toString() }.toSet()
        }
    }

    fun observeFavorites(context: Context): Flow<Set<Int>> =
        context.dataStore.data.map { prefs ->
            prefs[IS_FAVORITE]?.mapNotNull { it.toIntOrNull() }?.toSet() ?: emptySet()
        }
}
