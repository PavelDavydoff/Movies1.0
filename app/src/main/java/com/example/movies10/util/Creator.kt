package com.example.movies10.util

import android.content.Context
import com.example.movies10.data.MoviesRepositoryImpl
import com.example.movies10.data.network.RetrofitNetworkClient
import com.example.movies10.domain.api.MoviesInteractor
import com.example.movies10.domain.api.MoviesRepository
import com.example.movies10.domain.impl.MoviesInteractorImpl
import com.example.movies10.presentation.movies.MoviesSearchPresenter
import com.example.movies10.presentation.poster.PosterPresenter
import com.example.movies10.presentation.poster.PosterView

object Creator {
    private fun getMoviesRepository(context: Context): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient(context))
    }

    fun provideMoviesInteractor(context: Context): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository(context))
    }

    fun provideMoviesSearchPresenter(
        context: Context
    ): MoviesSearchPresenter {
        return MoviesSearchPresenter(context)
    }

    fun providePosterPresenter(posterView: PosterView, url: String): PosterPresenter {
        return PosterPresenter(posterView, url)
    }
}