package com.example.trabajo_3_apis.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PerrosFavoritos")
data class DogFavoritoEntity(
    @PrimaryKey val id: Int,
    val imagenUrl: String,
    // Momento en que se marco como favorito; sirve para ordenar.
    val agregadoEn: Long = System.currentTimeMillis()
)