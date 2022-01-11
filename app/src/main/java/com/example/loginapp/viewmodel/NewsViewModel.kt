package com.example.loginapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginapp.model.NewsData
import com.example.loginapp.repositories.NewsRepository

class NewsViewModel : ViewModel() {
    var news = MutableLiveData<NewsData?>()

    fun getNewsLiveData(): MutableLiveData<NewsData?> {
        return news
    }

    fun getNewsFromRepo(category: String) {
        NewsRepository.getFullNews(category)
        NewsRepository.onSuccess = {
            news.postValue(it)
        }
    }
}