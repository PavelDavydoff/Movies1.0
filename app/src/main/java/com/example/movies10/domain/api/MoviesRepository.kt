package com.example.movies10.domain.api

import com.example.movies10.domain.models.Movie
import com.example.movies10.util.Resource

interface MoviesRepository {
    fun searchMovies(expression: String): Resource<List<Movie>>
}