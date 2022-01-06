package com.example.loginapp.repositories

import androidx.lifecycle.MutableLiveData
import com.example.loginapp.api.NewsApiInterface
import com.example.loginapp.api.RetrofitInstance
import com.example.loginapp.model.NewsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object NewsRepository {

    private val apiService = RetrofitInstance.createApiService(NewsApiInterface::class.java)

    var onSuccess: ((NewsData?) -> Unit)? = null

    fun getFullNews(category: String) {
        val newsLiveData = MutableLiveData<NewsData?>()
        val apiCall = apiService.getNews(category)

        val result = apiCall.enqueue(object : Callback<NewsData> {
            override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {
                if (response.body()?.success == true) {
                    newsLiveData.postValue(response.body())
                    onSuccess?.invoke(response.body())
                } else {
                    newsLiveData.value = null
                }
            }

            override fun onFailure(call: Call<NewsData>, t: Throwable) {
                newsLiveData.value = null
            }

        })
    }
}