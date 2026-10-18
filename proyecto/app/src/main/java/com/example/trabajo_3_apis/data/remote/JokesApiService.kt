package com.example.trabajo_3_apis.data.remote

import retrofit2.http.GET

interface JokesApiService {
    @GET("jokes/random/")
    suspend fun obtenerBromas(): List<JokeDto>
}
