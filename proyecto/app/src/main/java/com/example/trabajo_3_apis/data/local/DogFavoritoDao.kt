package com.example.trabajo_3_apis.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DogFavoritoDao {

    @Query("SELECT * FROM PerrosFavoritos ORDER BY agregadoEn DESC")
    fun observarPerrosFavoritos(): Flow<List<DogFavoritoEntity>>

    @Query("SELECT id FROM PerrosFavoritos")
    fun observarIdsPerrosFavoritos(): Flow<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(favorito: DogFavoritoEntity)

    @Query("DELETE FROM PerrosFavoritos WHERE id = :id")
    suspend fun eliminarPorId(id: Int)
}
