package com.example.trabajo_3_apis.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "perros_cache")
data class DogCacheEntity(
    @PrimaryKey val id: Int,
    val imagenUrl: String
)
