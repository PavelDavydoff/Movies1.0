package com.example.movies10.util

import android.app.Activity
import android.content.Context
import com.example.movies10.data.MoviesRepositoryImpl
import com.example.movies10.data.network.RetrofitNetworkClient
import com.example.movies10.domain.api.MoviesInteractor
import com.example.movies10.domain.api.MoviesRepository
import com.example.movies10.domain.impl.MoviesInteractorImpl
import com.example.movies10.presentation.movies.MoviesSearchPresenter
import com.example.movies10.presentation.PosterController
import com.example.movies10.presentation.movies.MoviesView
import com.example.movies10.ui.movies.MoviesAdapter

object Creator {
    private fun getMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient(context))
    }

    fun provideMoviesInteractor(context: Context): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }

    fun provideMoviesSearchPresenter(
        moviesView: MoviesView,
        context: Context,
        adapter: MoviesAdapter
    ): MoviesSearchPresenter {
        return MoviesSearchPresenter(moviesView, context, adapter)
    }

    fun providePosterController(activity: Activity): PosterController {
        return PosterController(activity)
    }
}