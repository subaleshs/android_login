package com.example.loginapp.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.loginapp.model.NewsContent

//@Entity(tableName = "favorites", foreignKeys = [ForeignKey(
//    entity = UserEntity::class,
//    parentColumns = arrayOf ("uid"),
//    childColumns = arrayOf("userId"),
//    onDelete = ForeignKey.CASCADE)])

@Entity(tableName = "favorites")
data class FavouritesEntity(

    @Embedded
    val favouriteNews: NewsContent,
    val userId: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
