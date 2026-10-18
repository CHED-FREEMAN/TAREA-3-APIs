package com.example.trabajo_3_apis.data

import android.util.Log
import com.example.trabajo_3_apis.data.remote.RetrofitClient
import com.example.trabajo_3_apis.models.Broma
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class JokesRepository {
    private val api = RetrofitClient.apiJokes

    suspend fun obtenerBromas(): List<Broma> = withContext(Dispatchers.IO) {
        try {
            val dtos = api.obtenerBromas()
            Mapeador.mapList(dtos) { dto ->
                Broma(
                    setup = dto.setup ?: "¿Sin pregunta?",
                    punchline = dto.punchline ?: "Sin respuesta"
                )
            }
        } catch (e: Exception) {
            Log.e("JokesRepository", "Error obteniendo bromas: ${e.message}", e)
            throw e
        }
    }
}
