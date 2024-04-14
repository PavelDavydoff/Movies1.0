package com.example.movies10

import com.example.movies10.data.MoviesRepositoryImpl
import com.example.movies10.data.network.RetrofitNetworkClient
import com.example.movies10.domain.api.MoviesInteractor
import com.example.movies10.domain.api.MoviesRepository
import com.example.movies10.domain.impl.MoviesInteractorImpl

object Creator {
    private fun getMoviesRepository(): MoviesRepository {
        return MoviesRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideMoviesInteractor(): MoviesInteractor {
        return MoviesInteractorImpl(getMoviesRepository())
    }
}