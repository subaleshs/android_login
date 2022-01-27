package com.example.loginapp.repositories

import androidx.lifecycle.LiveData
import com.example.loginapp.db.FavouritesDao
import com.example.loginapp.db.FavouritesEntity


class FavoriteNewsRepository(private val dao: FavouritesDao) {

    fun getAllFavoriteNews(uid: String): LiveData<List<FavouritesEntity>> {
        return dao.getFavourites(uid)
    }

    suspend fun addToFavorites(news: FavouritesEntity) = dao.addTOFavourites(news)

    suspend fun deleteFavoriteNews(news: FavouritesEntity) = dao.deleteFavorites(news)

    suspend fun checkNewsExists(title: String, uid: String): FavouritesEntity{
        return dao.checkNewsExist(title,uid)
    }

    suspend fun del(id: Int) = dao.delete(id)
}