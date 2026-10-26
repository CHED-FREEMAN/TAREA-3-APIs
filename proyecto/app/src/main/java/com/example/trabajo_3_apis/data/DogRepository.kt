package com.example.trabajo_3_apis.data

import android.util.Log
import com.example.trabajo_3_apis.data.local.DogCacheDao
import com.example.trabajo_3_apis.data.local.DogFavoritoDao
import com.example.trabajo_3_apis.data.local.DogFavoritoEntity
import com.example.trabajo_3_apis.data.remote.DogApiService
import com.example.trabajo_3_apis.data.remote.RetrofitClient
import com.example.trabajo_3_apis.models.Perro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DogRepository(
    private val cacheDao: DogCacheDao,
    private val favoritoDao: DogFavoritoDao? = null,
    private val api: DogApiService = RetrofitClient.apiDog
) {
    fun observarPerros(): Flow<List<Perro>> =
        cacheDao.observarTodos().map { entidades -> entidades.map { it.aModelo() } }

    suspend fun hayPerrosGuardados(): Boolean = cacheDao.contar() > 0

    suspend fun cargarSiguientePagina(count: Int = 20): Resultado<Int> = llamarApi {
        val response = api.obtenerImagenesDePerros(count)
        val nuevos = response.message.map { Perro(message = it) }
        cacheDao.insertarTodos(nuevos.map { it.aEntidadCache() })
        nuevos.size
    }

    fun observarPerrosFavoritos(): Flow<List<DogFavoritoEntity>>? {
        return favoritoDao?.observarPerrosFavoritos()
    }

    fun observarIdsPerrosFavoritos(): Flow<List<Int>>? {
        return favoritoDao?.observarIdsPerrosFavoritos()
    }

    fun contarFavoritos(): Flow<Int>? {
        return favoritoDao?.contarFavoritos()
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

    suspend fun eliminarTodosLosFavoritos() = withContext(Dispatchers.IO) {
        favoritoDao?.eliminarTodos()
    }
}
