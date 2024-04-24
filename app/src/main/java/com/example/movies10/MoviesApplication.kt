package com.example.movies10

import android.app.Application
import com.example.movies10.presentation.movies.MoviesSearchPresenter

class MoviesApplication: Application() {
    var moviesSearchPresenter: MoviesSearchPresenter? = null
}