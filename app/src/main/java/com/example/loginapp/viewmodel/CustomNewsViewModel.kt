package com.example.loginapp.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.loginapp.db.CustomNewsEntity
import com.example.loginapp.db.NewsDB
import com.example.loginapp.db.NewsDao
import com.example.loginapp.repositories.CustomNewsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomNewsViewModel(application: Application) : AndroidViewModel(application) {

    private val dao: NewsDao = NewsDB(getApplication<Application>().applicationContext).getDao()
    private val repository: CustomNewsRepo = CustomNewsRepo(dao)

    fun getFullNews(userID: String): MutableLiveData<List<CustomNewsEntity>> {
        var newsLiveData: MutableLiveData<List<CustomNewsEntity>> = MutableLiveData()
        viewModelScope.launch(Dispatchers.IO) {
            newsLiveData.postValue(repository.getAllNews(userID))
        }
        return newsLiveData
    }

    fun addToDB(news: CustomNewsEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNewNews(news)
        }
    }

}