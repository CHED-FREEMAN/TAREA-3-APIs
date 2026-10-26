package com.example.trabajo_3_apis.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [DogFavoritoEntity::class, DogCacheEntity::class], version = 2, exportSchema = false)
abstract class DogDatabase : RoomDatabase() {

    abstract fun favoritoDao(): DogFavoritoDao
    abstract fun dogCacheDao(): DogCacheDao

    companion object {
        @Volatile
        private var instancia: DogDatabase? = null

        private val MIGRACION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `perros_cache` (" +
                        "`id` INTEGER NOT NULL, " +
                        "`imagenUrl` TEXT NOT NULL, " +
                        "PRIMARY KEY(`id`))"
                )
            }
        }

        fun obtener(context: Context): DogDatabase =
            instancia ?: synchronized(this) {
                instancia ?: Room.databaseBuilder(
                    context.applicationContext,
                    DogDatabase::class.java,
                    "pokedex.db"
                )
                    .addMigrations(MIGRACION_1_2)
                    .build()
                    .also { instancia = it }
            }
    }
}
