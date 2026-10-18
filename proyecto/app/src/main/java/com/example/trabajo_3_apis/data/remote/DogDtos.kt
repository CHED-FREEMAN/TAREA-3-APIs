package com.example.trabajo_3_apis.data.remote

import com.google.gson.annotations.SerializedName

data class DogImagesResponse(
    @SerializedName("message") val message: List<String>,
    @SerializedName("status") val status: String
)
