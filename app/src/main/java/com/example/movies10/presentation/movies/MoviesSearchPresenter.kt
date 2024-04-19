package com.example.movies10.presentation.movies

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.movies10.R
import com.example.movies10.domain.api.MoviesInteractor
import com.example.movies10.domain.models.Movie
import com.example.movies10.ui.movies.MoviesAdapter
import com.example.movies10.util.Creator

class MoviesSearchPresenter(
    private val view: MoviesView,
    private val context: Context,
    private val adapter: MoviesAdapter
) {

    private val moviesInteractor = Creator.provideMoviesInteractor(context)

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    private val movies = ArrayList<Movie>()

    private val handler = Handler(Looper.getMainLooper())

    private var lastSearchText: String? = null

    private val searchRunnable = Runnable {
        val newSearchText = lastSearchText ?: ""
        searchRequest(newSearchText)
    }

    fun searchDebounce(changedText: String) {
        this.lastSearchText = changedText
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    fun onCreate() {
        adapter.movies = movies
    }

    fun onDestroy() {
        handler.removeCallbacks(searchRunnable)
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {

            view.showPlaceholderMessage(false)
            view.showMoviesList(false)
            view.showProgressBar(true)

            moviesInteractor.searchMovies(
                newSearchText,
                object : MoviesInteractor.MoviesConsumer {
                    override fun consume(foundMovies: List<Movie>?, errorMessage: String?) {
                        handler.post {
                            view.showProgressBar(false)
                            if (foundMovies != null) {
                                movies.clear()
                                movies.addAll(foundMovies)
                                view.showMoviesList(true)
                                adapter.notifyDataSetChanged()
                            }
                            if (errorMessage != null) {
                                showMessage(
                                    context.getString(R.string.something_went_wrong),
                                    errorMessage
                                )
                            } else if (movies.isEmpty()) {
                                showMessage(context.getString(R.string.nothing_found), "")
                            } else {
                                hideMessage()
                            }
                        }
                    }
                })
        }
    }

    private fun showMessage(text: String, additionalMessage: String) {
        if (text.isNotEmpty()) {
            view.showPlaceholderMessage(true)
            movies.clear()
            adapter.notifyDataSetChanged()
            view.changePlaceholderText(text)
            if (additionalMessage.isNotEmpty()) {
                Toast.makeText(context, additionalMessage, Toast.LENGTH_LONG).show()
            } else {
                view.showPlaceholderMessage(false)
            }
        }
    }

    private fun hideMessage() {
        view.showPlaceholderMessage(false)
    }
}