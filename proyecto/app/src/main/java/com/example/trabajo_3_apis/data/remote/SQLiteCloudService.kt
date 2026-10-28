package com.example.trabajo_3_apis.data.remote

import android.util.Log
import com.example.trabajo_3_apis.data.local.DogFavoritoEntity
import com.example.trabajo_3_apis.models.Perro
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SQLiteCloudService {
    private val tag = "SQLiteCloudService"
    private val apiKey = "B5wXKlzOz34MpjOaQZmXbEtvOhWDVDEWGcbJPDcxa6s"
    private val databaseName = "dog-db.sqlite"
    private val api = RetrofitClient.apiSQLiteCloud

    suspend fun sincronizarAgregarFavorito(perro: Perro) = withContext(Dispatchers.IO) {
        try {
            val sql = "INSERT OR REPLACE INTO PerrosFavoritos (id, imagenUrl, agregadoEn) VALUES (${perro.id}, '${perro.message}', ${System.currentTimeMillis()});"
            val payload = SqlPayload(sql = sql)
            val authHeader = "Bearer $apiKey"
            val response = api.ejecutarSql(authHeader, databaseName, payload)
            if (response.isSuccessful) {
                Log.d(tag, "Favorito ${perro.id} insertado correctamente en SQLite Cloud.")
            } else {
                Log.e(tag, "Error al insertar favorito en SQLite Cloud: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e(tag, "Excepción al insertar favorito en SQLite Cloud: ${e.message}", e)
        }
    }

    suspend fun sincronizarEliminarFavorito(id: Int) = withContext(Dispatchers.IO) {
        try {
            val sql = "DELETE FROM PerrosFavoritos WHERE id = $id;"
            val payload = SqlPayload(sql = sql)
            val authHeader = "Bearer $apiKey"
            val response = api.ejecutarSql(authHeader, databaseName, payload)
            if (response.isSuccessful) {
                Log.d(tag, "Favorito $id borrado correctamente en SQLite Cloud.")
            } else {
                Log.e(tag, "Error al borrar favorito en SQLite Cloud: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e(tag, "Excepción al borrar favorito en SQLite Cloud: ${e.message}", e)
        }
    }

    suspend fun sincronizarCache(perros: List<Perro>) = withContext(Dispatchers.IO) {
        try {
            val sqlBuilder = StringBuilder()
            perros.forEach { perro ->
                sqlBuilder.append("INSERT OR REPLACE INTO perros_cache (id, imagenUrl) VALUES (${perro.id}, '${perro.message}');")
            }
            val sql = sqlBuilder.toString()
            if (sql.isNotEmpty()) {
                val payload = SqlPayload(sql = sql)
                val authHeader = "Bearer $apiKey"
                val response = api.ejecutarSql(authHeader, databaseName, payload)
                if (response.isSuccessful) {
                    Log.d(tag, "Caché de ${perros.size} perritos sincronizada correctamente en SQLite Cloud.")
                } else {
                    Log.e(tag, "Error al sincronizar caché en SQLite Cloud: ${response.errorBody()?.string()}")
                }
            }
        } catch (e: Exception) {
            Log.e(tag, "Excepción al sincronizar caché en SQLite Cloud: ${e.message}", e)
        }
    }

    suspend fun obtenerFavoritosRemotos(): List<DogFavoritoEntity> = withContext(Dispatchers.IO) {
        try {
            val sql = "SELECT id, imagenUrl, agregadoEn FROM PerrosFavoritos;"
            val payload = SqlPayload(sql = sql)
            val authHeader = "Bearer $apiKey"
            val response = api.ejecutarSql(authHeader, databaseName, payload)
            if (response.isSuccessful) {
                val jsonString = response.body()?.string() ?: return@withContext emptyList()
                // Parsear respuesta JSON de SQLite Cloud v2/sql
                val jsonElement = JsonParser.parseString(jsonString)
                val listaFavoritos = mutableListOf<DogFavoritoEntity>()
                
                if (jsonElement.isJsonObject) {
                    val obj = jsonElement.asJsonObject
                    // SQLite Cloud suele retornar un array de filas en "rows" o similar
                    if (obj.has("rows")) {
                        val rows = obj.getAsJsonArray("rows")
                        for (row in rows) {
                            val rowArr = row.asJsonArray
                            val id = rowArr[0].asInt
                            val imagenUrl = rowArr[1].asString
                            val agregadoEn = rowArr[2].asLong
                            listaFavoritos.add(DogFavoritoEntity(id = id, imagenUrl = imagenUrl, agregadoEn = agregadoEn))
                        }
                    }
                }
                return@withContext listaFavoritos
            }
        } catch (e: Exception) {
            Log.e(tag, "Excepción al obtener favoritos remotos: ${e.message}", e)
        }
        emptyList()
    }
}
