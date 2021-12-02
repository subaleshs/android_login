package com.example.loginapp

data class NewsTitle(
    val title: String,
    val date: String,
    val content: String,
    val imageUrl: String
)

data class NewsData(
    val category: String,
    val data: ArrayList<NewsTitle>
)

