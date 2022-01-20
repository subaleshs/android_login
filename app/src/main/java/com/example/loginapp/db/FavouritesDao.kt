package com.example.loginapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.loginapp.model.NewsContent

@Dao
interface FavouritesDao {

    @Insert
    fun addTOFavourites(favouritesNews: FavouritesEntity)

    @Query("SELECT * FROM FavouritesEntity")
    fun getFavourites(): List<FavouritesEntity>

}