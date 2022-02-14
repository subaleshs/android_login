package com.example.loginapp.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.loginapp.model.NewsContent


@Entity(tableName = "favorites")
data class FavouritesEntity(

    @Embedded
    val favouriteNews: NewsContent,
    val userId: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
