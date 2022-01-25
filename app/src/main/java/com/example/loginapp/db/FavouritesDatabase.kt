package com.example.loginapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavouritesEntity::class],
    version = 1
)
abstract class FavouritesDatabase: RoomDatabase() {

    abstract fun getDao(): FavouritesDao

    companion object {
        @Volatile var dbInstance: FavouritesDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = dbInstance ?: synchronized(LOCK) {
            dbInstance ?: buildDatabase(context).also {
                dbInstance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            FavouritesDatabase::class.java,
            "favourites"
        ).build()
    }
}