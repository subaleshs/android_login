package com.example.loginapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.loginapp.model.NewsContent

@Entity
data class FavouritesEntity(


    val favouriteNews: NewsContent
) {
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1
}