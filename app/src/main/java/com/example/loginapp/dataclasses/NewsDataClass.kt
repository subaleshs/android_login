package com.example.loginapp


data class NewsContent(
    val title: String,
    val date: String,
    val content: String,
    val imageUrl: String,
    val readMoreUrl: String
)

data class NewsData(
    val category: String,
    val data: ArrayList<NewsContent>
)

