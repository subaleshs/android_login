package com.example.loginapp.network

import com.example.loginapp.model.News
import com.example.loginapp.model.NewsData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiInterface {

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
    }

    @GET("news?")
    suspend fun getNews(@Query("category") category: String): Response<NewsData>

    @GET("top-headlines")
    suspend fun  getTopHeadlinesOfCountry(@Query("country") country: String): Response<News>

    @GET("everything")
    suspend fun getAllNews(@Query("q") q: String): Response<News>
}