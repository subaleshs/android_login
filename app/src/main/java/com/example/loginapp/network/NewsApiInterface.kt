package com.example.loginapp.network

import com.example.loginapp.model.NewsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiInterface {

    companion object {
        const val BASE_URL = "https://inshortsapi.vercel.app/"
    }

    @GET("news?")
    fun getNews(@Query("category") category: String): Call<NewsData>
}