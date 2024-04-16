package com.example.movies10.data

import com.example.movies10.data.dto.MoviesSearchRequest
import com.example.movies10.data.dto.MoviesSearchResponse
import com.example.movies10.domain.api.MoviesRepository
import com.example.movies10.domain.models.Movie
import com.example.movies10.util.Resource

class MoviesRepositoryImpl(private val networkClient: NetworkClient) : MoviesRepository {
    override fun searchMovies(expression: String): Resource<List<Movie>> {
        val response = networkClient.doRequest(MoviesSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> {
                Resource.Error("Проверьте подключение к интернету")
            }

            200 -> {
                Resource.Succes((response as MoviesSearchResponse).results.map {
                    Movie(it.id, it.resultType, it.image, it.title, it.description)
                })
            }

            else -> {
                Resource.Error("Ошибка сервера")
            }
        }
    }
}