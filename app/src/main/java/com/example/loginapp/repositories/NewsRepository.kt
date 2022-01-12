package com.example.loginapp.repositories

import androidx.lifecycle.MutableLiveData
import com.example.loginapp.network.NewsApiInterface
import com.example.loginapp.network.RetrofitInstance
import com.example.loginapp.model.NewsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object NewsRepository {

    private val apiService = RetrofitInstance.createApiService(NewsApiInterface::class.java)

    var onSuccess: ((NewsData?) -> Unit)? = null
    var onFailureListener: (() -> Unit)? = null

    fun getFullNews(category: String) {
        val newsLiveData = MutableLiveData<NewsData?>()
        CoroutineScope(Dispatchers.IO).launch {
            val apiCall = apiService.getNews(category)

            if (apiCall.isSuccessful) {
                onSuccess?.invoke(apiCall.body())
            } else {
                onFailureListener?.invoke()
            }
        }

    }
}