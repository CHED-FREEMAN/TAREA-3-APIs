package com.example.trabajo_3_apis.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.jvm.java

@Database(entities = [DogFavoritoEntity::class], version = 1, exportSchema = false)
abstract class DogDatabase : RoomDatabase() {

    abstract fun favoritoDao(): DogFavoritoDao

    companion object {
        @Volatile
        private var instancia: DogDatabase? = null

        // Singleton: una sola instancia de la base de datos en toda la app.
        fun obtener(context: Context): DogDatabase =
            instancia ?: synchronized(this) {
                instancia ?: Room.databaseBuilder(
                    context.applicationContext,
                    DogDatabase::class.java,
                    "pokedex.db"
                ).build().also { instancia = it }
            }
    }
}