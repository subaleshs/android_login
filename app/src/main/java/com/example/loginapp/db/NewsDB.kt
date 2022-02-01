package com.example.loginapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [CustomNewsEntity::class],
    version = 1
)
abstract class NewsDB: RoomDatabase() {
    abstract fun getDao(): NewsDao

    companion object {
        @Volatile var dbInstance: NewsDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = dbInstance ?: synchronized(LOCK) {
            dbInstance ?: buildDatabase(context).also {
                dbInstance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            NewsDB::class.java,
            "news"
        ).build()
    }
}