package com.example.trabajo_3_apis.data.remote

import com.google.gson.annotations.SerializedName

data class CartoonDto(
    @SerializedName("title") val title: String?,
    @SerializedName("year") val year: Any?,
    @SerializedName("image") val image: String?,
    @SerializedName("creator") val creator: Any?
)
