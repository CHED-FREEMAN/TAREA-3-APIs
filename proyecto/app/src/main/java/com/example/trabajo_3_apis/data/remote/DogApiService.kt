package com.example.trabajo_3_apis.data.remote


import retrofit2.http.GET
import retrofit2.http.Query
interface DogApiService {
    @GET("breeds/image/random")
    suspend fun obtenerImagenDePerroAleatoria(): DogImageResponse
}
