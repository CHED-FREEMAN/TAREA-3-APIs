package com.example.trabajo_3_apis.data

sealed class Resultado<out T> {
    data class Exito<T>(val datos: T) : Resultado<T>()
    data class Error(val mensaje: String) : Resultado<Nothing>()
}

suspend fun <T> llamarApi(bloque: suspend () -> T): Resultado<T> {
    return try {
        Resultado.Exito(bloque())
    } catch (e: Exception) {
        Resultado.Error(e.message ?: "Error desconocido")
    }
}
