package com.example.loginapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.loginapp.db.FavouritesDao
import com.example.loginapp.db.FavouritesDatabase
import com.example.loginapp.db.FavouritesEntity
import com.example.loginapp.repositories.FavoriteNewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private var favoritesLiveData: LiveData<List<FavouritesEntity>>
    private var dao: FavouritesDao =  FavouritesDatabase(getApplication<Application>().applicationContext).getDao()
    private val repository = FavoriteNewsRepository(dao)
    private var currentUserId: String = AuthViewModel().getCurrentUser()?.uid ?: "user"

    init {
        favoritesLiveData = repository.getAllFavoriteNews(currentUserId)
    }


    fun getFavoritesLiveData(): LiveData<List<FavouritesEntity>> {
        return favoritesLiveData
    }

    fun checkForNewsInDb(title: String): MutableLiveData<FavouritesEntity?> {
        val newsEntityLivaData = MutableLiveData<FavouritesEntity?>()
        viewModelScope.launch(Dispatchers.IO) {
            newsEntityLivaData.postValue(repository.checkNewsExists(title, currentUserId))
        }
        return newsEntityLivaData
    }
    fun addToFavorites(news: FavouritesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addToFavorites(news)
        }
    }

    fun deleteFromFavorites(news: FavouritesEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavoriteNews(news)
        }
    }
}