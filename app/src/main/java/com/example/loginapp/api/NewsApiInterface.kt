package com.example.loginapp.api

import com.example.loginapp.model.NewsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiInterface {

    @GET("news?")
    fun getNews(@Query("category") category: String): Call<NewsData>
}