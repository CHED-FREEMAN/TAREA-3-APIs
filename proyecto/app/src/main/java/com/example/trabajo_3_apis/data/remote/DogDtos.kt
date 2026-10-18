package com.example.trabajo_3_apis.data.remote

import com.google.gson.annotations.SerializedName

data class DogImageResponse(
    @SerializedName("message") val message: String,
)