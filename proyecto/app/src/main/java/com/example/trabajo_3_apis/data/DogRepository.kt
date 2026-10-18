package com.example.trabajo_3_apis.data

import android.util.Log
import com.example.trabajo_3_apis.data.remote.RetrofitClient
import com.example.trabajo_3_apis.models.Perro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogRepository {
    private val api = RetrofitClient.apiDog

    suspend fun obtenerPerros(count: Int = 10): List<Perro> = withContext(Dispatchers.IO) {
        try {
            val response = api.obtenerImagenesDePerros(count)
            Mapeador.mapList(response.message) { imageUrl ->
                Perro(message = imageUrl)
            }
        } catch (e: Exception) {
            Log.e("DogRepository", "Error obteniendo perros: ${e.message}", e)
            throw e
        }
    }
}
