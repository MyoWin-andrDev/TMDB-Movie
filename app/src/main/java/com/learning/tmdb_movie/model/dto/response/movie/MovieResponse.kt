package com.learning.tmdb_movie.model.dto.response.movie

import com.google.gson.annotations.SerializedName

data class MovieResponse(

    @SerializedName("results")
    val results: List<MovieItemModel?>? = null
)

data class MovieItemModel(

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("vote_average")
    val voteAverage: Double? = null,

    @SerializedName("id")
    val id: Int? = null,

    var isFavorite: Boolean = false,
)