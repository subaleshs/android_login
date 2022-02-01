package com.example.loginapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NewsDao {
    @Insert
    suspend fun addNews(newsEntity: CustomNewsEntity)

    @Query("SELECT * FROM CUSTOM_NEWS WHERE uid LIKE :userID")
    suspend fun getNews(userID: String): List<CustomNewsEntity>
}

