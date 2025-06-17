package com.learning.tmdb_movie.model.Favourite

import com.google.firebase.firestore.PropertyName

data class FavouriteResponse(
    @get:PropertyName("id") @set:PropertyName("id")
    var movieId : Int = 0,

    @get:PropertyName("is_fav") @set:PropertyName("is_fav")
    var isFavourite : Boolean = false
)
