package com.example.movies10.presentation.movies

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.example.movies10.R
import com.example.movies10.domain.api.MoviesInteractor
import com.example.movies10.domain.models.Movie
import com.example.movies10.ui.movies.models.MoviesState
import com.example.movies10.util.Creator

class MoviesSearchPresenter(
    private val view: MoviesView, private val context: Context
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

    fun onDestroy() {
        handler.removeCallbacks(searchRunnable)
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {

            view.render(MoviesState.Loading)

            moviesInteractor.searchMovies(newSearchText, object : MoviesInteractor.MoviesConsumer {
                override fun consume(foundMovies: List<Movie>?, errorMessage: String?) {
                    handler.post {
                        if (foundMovies != null) {
                            movies.clear()
                            movies.addAll(foundMovies)
                        }
                        when {
                            errorMessage != null -> {
                                view.render(MoviesState.Error(context.getString(R.string.something_went_wrong)))
                                view.showToast(errorMessage)
                            }

                            movies.isEmpty() -> {
                                view.render(MoviesState.Empty(context.getString(R.string.nothing_found)))
                            }

                            else -> {
                                view.render(MoviesState.Content(movies))
                            }
                        }
                    }
                }
            })
        }
    }

    /*private fun showMessage(text: String, additionalMessage: String) {
        if (text.isNotEmpty()) {
            view.showPlaceholderMessage(true)
            movies.clear()
            view.updateMoviesList(movies)
            view.changePlaceholderText(text)
            if (additionalMessage.isNotEmpty()) {
                view.showToast(additionalMessage)
            } else {
                view.showPlaceholderMessage(false)
            }
        }
    }

    private fun hideMessage() {
        view.showPlaceholderMessage(false)
    }*/
}