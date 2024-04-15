package com.example.movies10

import android.app.Activity
import com.example.movies10.data.MoviesRepositoryImpl
import com.example.movies10.data.network.RetrofitNetworkClient
import com.example.movies10.domain.api.MoviesInteractor
import com.example.movies10.domain.api.MoviesRepository
import com.example.movies10.domain.impl.MoviesInteractorImpl
import com.example.movies10.presentation.MoviesSearchController
import com.example.movies10.presentation.PosterController
import com.example.movies10.ui.movies.MoviesAdapter

object Creator {
    private fun getMoviesRepository(): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideMoviesInteractor(): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository())
    }

    fun provideMoviesSearchController(
        activity: Activity,
        adapter: MoviesAdapter
    ): MoviesSearchController {
        return MoviesSearchController(activity, adapter)
    }

    fun providePosterController(activity: Activity): PosterController {
        return PosterController(activity)
    }
}