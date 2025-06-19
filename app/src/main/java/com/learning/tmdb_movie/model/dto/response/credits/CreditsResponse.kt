package com.learning.tmdb_movie.model.dto.response.credits

import com.google.gson.annotations.SerializedName

data class CreditsResponse(

	@field:SerializedName("cast")
	val cast: List<CastItem?>? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class CastItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("profile_path")
	val profilePath: String? = null
)
