package com.learning.tmdb_movie.model.ui_model

import com.learning.tmdb_movie.model.dto.response.detail.DetailResponse

data class DetailUiModel(
    val detail: DetailResponse,
    val isFavorite: Boolean
)
