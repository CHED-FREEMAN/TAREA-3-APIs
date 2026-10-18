package com.example.trabajo_3_apis.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiService {
    @GET("breeds/image/random/{count}")
    suspend fun obtenerImagenesDePerros(@Path("count") count: Int): DogImagesResponse
}
