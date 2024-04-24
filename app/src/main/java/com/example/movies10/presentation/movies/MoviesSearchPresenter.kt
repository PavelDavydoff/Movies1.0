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
     private val context: Context
) {
    private var view: MoviesView? = null
    private var state: MoviesState? = null
    private var latestSearchText: String? = null

    private val moviesInteractor = Creator.provideMoviesInteractor(context)

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    private val handler = Handler(Looper.getMainLooper())

    private val searchRunnable = Runnable {
        val newSearchText = latestSearchText ?: ""
        searchRequest(newSearchText)
    }

    fun attachView(view: MoviesView){
        this.view = view
        state?.let { view.render(it) }
    }
    fun detachView(){
        this.view = null
    }

    fun searchDebounce(changedText: String) {
        if (latestSearchText == changedText){
            return
        }
        this.latestSearchText = changedText
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    fun onDestroy() {
        handler.removeCallbacks(searchRunnable)
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(MoviesState.Loading)

            moviesInteractor.searchMovies(newSearchText, object : MoviesInteractor.MoviesConsumer {
                override fun consume(foundMovies: List<Movie>?, errorMessage: String?) {
                    handler.post {
                        val movies = mutableListOf<Movie>()
                        if (foundMovies != null) {
                            movies.addAll(foundMovies)
                        }
                        when {
                            errorMessage != null -> {
                                renderState(MoviesState.Error(context.getString(R.string.something_went_wrong)))
                                view?.showToast(errorMessage)
                            }

                            movies.isEmpty() -> {
                                renderState(MoviesState.Empty(context.getString(R.string.nothing_found)))
                            }

                            else -> {
                                renderState(MoviesState.Content(movies))
                            }
                        }
                    }
                }
            })
        }
    }

    private fun renderState(state: MoviesState){
        this.state = state
        this.view?.render(state)
    }

}