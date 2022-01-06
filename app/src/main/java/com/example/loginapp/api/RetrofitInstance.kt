package com.example.loginapp.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit = Retrofit.Builder().baseUrl(NewsApiInterface.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(getClient())
        .build()

    private fun getClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    fun <T> createApiService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}