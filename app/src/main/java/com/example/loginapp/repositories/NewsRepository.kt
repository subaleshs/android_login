package com.example.loginapp.repositories

import android.util.Log
import com.example.loginapp.model.News
import com.example.loginapp.network.NewsApiInterface
import com.example.loginapp.network.RetrofitInstance
import com.example.loginapp.model.NewsData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object NewsRepository {

    private val apiService = RetrofitInstance.createApiService(NewsApiInterface::class.java)

    var onSuccess: ((News?) -> Unit)? = null
    var onFailureListener: (() -> Unit)? = null

//    fun getFullNews(category: String) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val apiCall = apiService.getNews(category)
//
//            if (apiCall.isSuccessful) {
//                onSuccess?.invoke(apiCall.body())
//            } else {
//                onFailureListener?.invoke()
//            }
//        }
//
//    }

    fun getEveryNews() {
        CoroutineScope(Dispatchers.IO).launch {
            val apiCall = apiService.getAllNews("india")
            Log.d("ty", apiCall.raw().toString())
            if (apiCall.isSuccessful) {
                Log.d("res", apiCall.body().toString())
                onSuccess?.invoke(apiCall.body())
            } else {
                Log.d("res2", apiCall.body().toString())
                onFailureListener?.invoke()
            }
        }
    }
}