package com.example.trabajo_3_apis.data

object Mapeador {

    /**
     * Función genérica única para mapear una lista de DTOs a cualquier modelo de dominio
     * utilizando una función de transformación compartida.
     */
    fun <T, R> mapList(dtos: List<T>, transform: (T) -> R): List<R> {
        return dtos.map(transform)
    }

    /**
     * Función genérica única para mapear un único DTO a un modelo de dominio
     * utilizando una función de transformación compartida.
     */
    fun <T, R> mapItem(dto: T, transform: (T) -> R): R {
        return transform(dto)
    }
}
