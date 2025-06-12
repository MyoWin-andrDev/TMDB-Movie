package com.learning.tmdb_movie.model.Movie

import com.google.gson.annotations.SerializedName
import com.learning.tmdb_movie.model.MovieEntityModel

data class UpcomingResponse(

	@SerializedName("results")
	val results: List<MovieEntityModel?>? = null
)