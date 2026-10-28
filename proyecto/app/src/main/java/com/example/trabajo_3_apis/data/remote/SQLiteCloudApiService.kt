package com.example.trabajo_3_apis.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

data class SqlPayload(
    val sql: String
)

interface SQLiteCloudApiService {
    @POST("v2/sql")
    suspend fun ejecutarSql(
        @Header("Authorization") authorization: String,
        @Query("database") database: String,
        @Body payload: SqlPayload
    ): Response<ResponseBody>
}
