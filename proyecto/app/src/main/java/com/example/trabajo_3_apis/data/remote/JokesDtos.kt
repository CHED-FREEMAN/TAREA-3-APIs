package com.example.trabajo_3_apis.data.remote

import com.google.gson.annotations.SerializedName

data class JokeDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("type") val type: String?,
    @SerializedName("setup") val setup: String?,
    @SerializedName("punchline") val punchline: String?
)
