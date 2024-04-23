package com.example.movies10.presentation.movies

import com.example.movies10.domain.models.Movie
import com.example.movies10.ui.movies.models.MoviesState

interface MoviesView {
    /* fun showPlaceholderMessage(isVisible: Boolean)
     fun showMoviesList(isVisible: Boolean)
     fun showProgressBar(isVisible: Boolean)
     fun changePlaceholderText(newPlaceholderText: String)
     fun updateMoviesList(newMoviesList: List<Movie>)*/

    fun render(state: MoviesState)
    fun showToast(message: String)
    /*fun showLoading()
    fun showError(errorMessage: String)
    fun showEmpty(emptyMessage: String)
    fun showContent(movies: List<Movie>)*/
}