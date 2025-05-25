package com.learning.tmdb_movie.Util

import retrofit2.Response


fun Response<*>.getErrorMessage() : String =
    "Error: ${this.errorBody()?.string()}"
