package com.example.movies10.domain.api

import com.example.movies10.domain.models.Movie

interface MoviesRepository {
    fun searchMovies(expression: String): List<Movie>
}