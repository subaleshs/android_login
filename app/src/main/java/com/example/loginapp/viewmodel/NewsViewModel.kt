package com.example.loginapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginapp.model.News
import com.example.loginapp.model.NewsData
import com.example.loginapp.repositories.NewsRepository

class NewsViewModel : ViewModel() {
    var news = MutableLiveData<News?>()

    fun getNewsLiveData(): MutableLiveData<News?> {
        return news
    }

    fun getNewsFromRepo(category: String) {

//        NewsRepository.getFullNews(category)
        NewsRepository.getEveryNews()
        NewsRepository.onSuccess = {
            news.postValue(it)
        }
        NewsRepository.onFailureListener = {
            news.postValue(null)
        }
    }
}