package com.example.trabajo_3_apis.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.jvm.java

object RetrofitClient {
    private const val BASE_URL_DOG = "https://dog.ceo/api/"
    private const val BASE_URL_CARTOONS = "https://api.sampleapis.com/"
    private const val BASE_URL_JOKES = "https://official-joke-api.appspot.com/"
    private const val BASE_URL_SQLITE_CLOUD = "https://cewel8qjdz.g1.gateway.sqlite.cloud/"

    private val clienteHttp: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        .build()

    val apiDog: DogApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_DOG)
            .client(clienteHttp)
            .addConverterFactory(GsonConverterFactory.create()) // JSON <-> objetos Kotlin
            .build()
            .create(DogApiService::class.java)
    }
    val apiCartoons: CartoonsApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_CARTOONS)
            .client(clienteHttp)
            .addConverterFactory(GsonConverterFactory.create()) // JSON <-> objetos Kotlin
            .build()
            .create(CartoonsApiService::class.java)
    }
    val apiJokes: JokesApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_JOKES)
            .client(clienteHttp)
            .addConverterFactory(GsonConverterFactory.create()) // JSON <-> objetos Kotlin
            .build()
            .create(JokesApiService::class.java)
    }
    val apiSQLiteCloud: SQLiteCloudApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_SQLITE_CLOUD)
            .client(clienteHttp)
            .addConverterFactory(GsonConverterFactory.create()) // JSON <-> objetos Kotlin
            .build()
            .create(SQLiteCloudApiService::class.java)
    }
}
