package com.learning.tmdb_movie.model.MovieFavourite

import com.google.firebase.firestore.PropertyName

data class MovieFavModel(
    @get:PropertyName("id") @set:PropertyName("id")
    var movieId : Int = 0,
    @get:PropertyName("is_fav") @set:PropertyName("is_fav")
    var isFavourite : Boolean = false
)
