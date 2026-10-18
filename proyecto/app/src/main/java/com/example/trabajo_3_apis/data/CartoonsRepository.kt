package com.example.trabajo_3_apis.data

import android.util.Log
import com.example.trabajo_3_apis.data.remote.RetrofitClient
import com.example.trabajo_3_apis.models.Caricatura
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartoonsRepository {
    private val api = RetrofitClient.apiCartoons

    suspend fun obtenerCaricaturas(): List<Caricatura> = withContext(Dispatchers.IO) {
        try {
            val dtos = api.obtenerCartoons()
            Mapeador.mapList(dtos) { dto ->
                val parsedYear = when (dto.year) {
                    is Number -> dto.year.toInt()
                    is String -> dto.year.toIntOrNull() ?: 0
                    else -> 0
                }
                val creatorString = when (dto.creator) {
                    is List<*> -> dto.creator.joinToString(", ") { it.toString() }
                    is String -> dto.creator
                    else -> "Desconocido"
                }
                Caricatura(
                    title = dto.title ?: "Sin título",
                    year = parsedYear,
                    image = dto.image ?: "",
                    creator = creatorString
                )
            }
        } catch (e: Exception) {
            Log.e("CartoonsRepository", "Error obteningendo caricaturas: ${e.message}", e)
            throw e
        }
    }
}
