package com.example.loginapp.repositories

import androidx.lifecycle.LiveData
import com.example.loginapp.db.FavouritesDao
import com.example.loginapp.db.FavouritesEntity


class FavoriteNewsRepository(private val favoritesDao: FavouritesDao) {

    fun getAllFavoriteNews(): LiveData<List<FavouritesEntity>> {
        return favoritesDao.getFavourites()
    }

    suspend fun addToFavorites(news: FavouritesEntity) = favoritesDao.addTOFavourites(news)

    suspend fun deleteFavoriteNews(news: FavouritesEntity) = favoritesDao.deleteFavorites(news)

    suspend fun checkNewsExists(title: String): FavouritesEntity{
        return favoritesDao.checkNewsExist(title)
    }

    suspend fun del(id: Int) = favoritesDao.delete(id)

}