package com.example.loginapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavouritesDao {

    @Insert
    suspend fun addTOFavourites(favouritesNews: FavouritesEntity)

    @Query("SELECT * FROM FavouritesEntity ORDER BY id ASC")
    fun getFavourites(): LiveData<List<FavouritesEntity>>

    @Query("SELECT * FROM FavouritesEntity WHERE title LIKE :title")
    suspend fun checkNewsExist(title: String): FavouritesEntity

    @Delete
    suspend fun deleteFavorites(favNews: FavouritesEntity)

    @Query("DELETE FROM FavouritesEntity WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM FavouritesEntity")
    suspend fun del()

}