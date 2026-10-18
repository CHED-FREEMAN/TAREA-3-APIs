package com.example.trabajo_3_apis.data.remote

import retrofit2.http.GET

interface JokesApiService {
    @GET("jokes/random/250")
    suspend fun obtenerBromas(): List<JokeDto>
}
