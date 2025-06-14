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
const val AUTHENTICATION = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyNzcxNDkwMjY5MjJiZmQ0YTY4MmYyZWFiYTNkOGFiZiIsIm5iZiI6MTcyOTM1NzY1NS45MDMsInN1YiI6IjY3MTNlNzU3OTlmMjJmMzI2YWFkMjJhOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.3U7gKSIZzK6vT95GbH7iFmSOYcEstk1vBKCx9sLwPtU"
const val ACCEPT = "application/json"
//API PARAMS
const val LANGUAGE = "en-US"
const val PAGE = 1