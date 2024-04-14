package com.example.movies10.data.dto

import com.example.movies10.domain.models.Movie

class MoviesSearchResponse(val searchType: String,
                           val expression: String,
                           val results: List<MovieDto>) : Response()