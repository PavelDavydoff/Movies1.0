package com.example.movies10.presentation.movies

interface MoviesView {
    fun showPlaceholderMessage(isVisible: Boolean)
    fun showMoviesList(isVisible: Boolean)
    fun showProgressBar(isVisible: Boolean)
    fun changePlaceholderText(newPlaceholderText: String)
}