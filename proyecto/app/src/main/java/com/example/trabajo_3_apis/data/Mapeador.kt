package com.example.trabajo_3_apis.data

import com.example.trabajo_3_apis.data.local.DogCacheEntity
import com.example.trabajo_3_apis.models.Perro

object Mapeador {
    fun <T, R> mapList(dtos: List<T>, transform: (T) -> R): List<R> {
        return dtos.map(transform)
    }

    fun <T, R> mapItem(dto: T, transform: (T) -> R): R {
        return transform(dto)
    }
}

fun DogCacheEntity.aModelo(): Perro = Perro(message = imagenUrl)
fun Perro.aEntidadCache(): DogCacheEntity = DogCacheEntity(id = id, imagenUrl = message)
