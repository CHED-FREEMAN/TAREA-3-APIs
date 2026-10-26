package com.example.trabajo_3_apis.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DogCacheDao {
    @Query("SELECT * FROM perros_cache")
    fun observarTodos(): Flow<List<DogCacheEntity>>

    @Query("SELECT COUNT(*) FROM perros_cache")
    suspend fun contar(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodos(perros: List<DogCacheEntity>)
}
