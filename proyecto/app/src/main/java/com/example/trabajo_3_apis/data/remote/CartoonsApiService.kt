package com.example.trabajo_3_apis.data.remote

import retrofit2.http.GET

interface CartoonsApiService {
    @GET("cartoons/cartoons2D")
    suspend fun obtenerCartoons(): List<CartoonDto>
}
