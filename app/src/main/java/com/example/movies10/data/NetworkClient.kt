package com.example.movies10.data

import com.example.movies10.data.dto.Response
interface NetworkClient {
    fun doRequest(dto: Any): Response

}