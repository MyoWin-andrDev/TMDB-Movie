package com.learning.tmdb_movie.Util

//BASE URL
const val BASE_URL = "https://api.themoviedb.org/3/movie/"
const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
//MOVIE API ENDPOINT
const val RECOMMEND_ENDPOINT = "popular"
const val NOW_PLAYING_ENDPOINT = "now_playing"
const val UPCOMING_ENDPOINT = "upcoming"

//INTENT
const val INTENT_ID = "MOVIE_ID"

//Detail API ENDPOINT
const val DETAIL_ENDPOINT = ""
//Headers
const val AUTHENTICATION = "Bearer *******************************************************"
const val ACCEPT = "application/json"
//API PARAMS
const val LANGUAGE = "en-US"
const val PAGE = 1
//Favourite Collection
const val FAV_COLLECTION = "FAVOURITES"
//Operation Status
const val ADD_MSG = "Added to favourite"
const val REMOVE_MSG = "Removed from favourite"
//Error
const val INIT_LOAD_FAILED = "Initial load failed:"
const val FIRE_STORE_ERROR = "Firestore Error "
const val REAL_TIME_ERROR = "Real-time update error"
const val TOGGLE_ERROR = "Toggle Failed :"