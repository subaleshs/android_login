package com.example.loginapp.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {

        const val BASE_URL = "https://inshortsapi.vercel.app/"

        fun getRetrofit(): Retrofit {
            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build()
        }

        private fun getClient(): OkHttpClient {
            return  OkHttpClient.Builder().build()
        }
    }
}