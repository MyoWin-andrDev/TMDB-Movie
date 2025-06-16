package com.learning.tmdb_movie.service.database

import com.google.firebase.firestore.FirebaseFirestore
import com.learning.tmdb_movie.Util.FAV_COLLECTION

object FireStoreInstance {
    private val favFireStore : FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    fun getFavCollection() = favFireStore.collection(FAV_COLLECTION)
}