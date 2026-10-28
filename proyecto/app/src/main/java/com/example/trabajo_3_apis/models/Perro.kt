package com.example.trabajo_3_apis.models

class Perro(
    val message: String,
    val id: Int = message.hashCode()
)
