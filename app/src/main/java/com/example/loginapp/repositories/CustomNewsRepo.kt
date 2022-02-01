package com.example.loginapp.repositories

import androidx.lifecycle.LiveData
import com.example.loginapp.db.CustomNewsEntity
import com.example.loginapp.db.NewsDao

class CustomNewsRepo(private val dao: NewsDao) {

    suspend fun getAllNews(userID: String): List<CustomNewsEntity> {
        return dao.getNews(userID)
    }

    suspend fun addNewNews(news: CustomNewsEntity) = dao.addNews(news)

}