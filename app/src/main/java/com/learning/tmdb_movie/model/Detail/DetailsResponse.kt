package com.learning.tmdb_movie.model.Detail

import com.google.gson.annotations.SerializedName

data class DetailResponse(

	@field:SerializedName("backdrop_path")
	val backdropPath: String? = null,

	@field:SerializedName("overview")
	val overview: String? = null,

	@field:SerializedName("spoken_languages")
	val spokenLanguages: List<SpokenLanguagesItem?>? = null,

	@field:SerializedName("release_date")
	val releaseDate: String? = null,

	@field:SerializedName("genres")
	val genres: List<GenresItem?>? = null,

	@field:SerializedName("vote_average")
	val voteAverage: Any? = null,

	@field:SerializedName("production_countries")
	val productionCountries: List<ProductionCountriesItem?>? = null,

	@field:SerializedName("runtime")
	val runtime: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("vote_count")
	val voteCount: Int? = null
)

data class SpokenLanguagesItem(

	@field:SerializedName("name")
	val name: String? = null
)

data class GenresItem(

	@field:SerializedName("name")
	val name: String? = null
)

data class ProductionCountriesItem(

	@field:SerializedName("iso_3166_1")
	val iso31661: String? = null
)
