package com.example.loginapp.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavouritesDao {

    @Insert
    suspend fun addTOFavourites(favouritesNews: FavouritesEntity)

    @Query("SELECT * FROM favorites WHERE userId LIKE :uid ORDER BY id ASC")
    fun getFavourites(uid: String): LiveData<List<FavouritesEntity>>

    @Query("SELECT * FROM favorites WHERE title LIKE :title AND userId LIKE :uid")
    suspend fun checkNewsExist(title: String, uid: String): FavouritesEntity

    @Delete
    suspend fun deleteFavorites(favNews: FavouritesEntity)

    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM favorites")
    suspend fun del()
}