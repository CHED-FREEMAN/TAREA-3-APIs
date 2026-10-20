package com.example.trabajo_3_apis.data

import android.util.Log
import com.example.trabajo_3_apis.data.local.DogFavoritoDao
import com.example.trabajo_3_apis.data.local.DogFavoritoEntity
import com.example.trabajo_3_apis.data.remote.RetrofitClient
import com.example.trabajo_3_apis.models.Perro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class DogRepository(
    private val favoritoDao: DogFavoritoDao? = null
) {
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

    fun observarPerrosFavoritos(): Flow<List<DogFavoritoEntity>>? {
        return favoritoDao?.observarPerrosFavoritos()
    }

    fun observarIdsPerrosFavoritos(): Flow<List<Int>>? {
        return favoritoDao?.observarIdsPerrosFavoritos()
    }

    suspend fun agregarFavorito(perro: Perro) = withContext(Dispatchers.IO) {
        favoritoDao?.insertar(
            DogFavoritoEntity(
                id = perro.id,
                imagenUrl = perro.message
            )
        )
    }

    suspend fun eliminarFavorito(id: Int) = withContext(Dispatchers.IO) {
        favoritoDao?.eliminarPorId(id)
    }
}
