package com.example.loginapp.repositories

import com.example.loginapp.network.NewsApiInterface
import com.example.loginapp.network.RetrofitInstance
import com.example.loginapp.model.NewsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object NewsRepository {

    private val apiService = RetrofitInstance.createApiService(NewsApiInterface::class.java)

    var onSuccess: ((NewsData?) -> Unit)? = null
    var onFailureListener: (() -> Unit)? = null

    fun getFullNews(category: String) {
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