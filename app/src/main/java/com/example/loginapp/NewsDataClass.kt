package com.example.loginapp

data class NewsTitle(
    val title: String,
    val author: String,
    val date: String,
)

data class NewsData(
    val category: String,
    val data: ArrayList<NewsTitle>
)

