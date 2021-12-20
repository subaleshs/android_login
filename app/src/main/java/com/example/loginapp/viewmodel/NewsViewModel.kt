package com.example.loginapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginapp.api.NewsApiInterface
import com.example.loginapp.api.RetrofitInstance
import com.example.loginapp.model.NewsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {
    val news = MutableLiveData<NewsData?>()

    fun getLiveData(): MutableLiveData<NewsData?> {
        return  news
    }

    fun getNewsfromAPI(category: String) {
        val retrofitInstance = RetrofitInstance.getRetrofit()
        val api = retrofitInstance.create(NewsApiInterface::class.java)
        val apiCall = api.getNews(category)

        val result = apiCall.enqueue(object : Callback<NewsData> {
            override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {
                news.value = response.body()
            }

            override fun onFailure(call: Call<NewsData>, t: Throwable) {
                news.value = null
            }
        })
    }
}