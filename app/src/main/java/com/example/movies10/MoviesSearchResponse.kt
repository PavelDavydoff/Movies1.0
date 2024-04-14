package com.example.movies10

data class MoviesSearchResponse(val searchType: String,
                                val expression: String,
                                val results: List<Movie>)