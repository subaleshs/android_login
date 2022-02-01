package com.example.loginapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_news")
data class CustomNewsEntity(
    val title: String,
    val content: String,
    val date: String,
    val imagePath: String,
    val uid: String
) {
    @PrimaryKey(autoGenerate = true)
    var newsId: Int = 1
}