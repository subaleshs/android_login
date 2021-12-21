package com.example.loginapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsContent(
    val title: String?,
    val date: String?,
    val content: String?,
    val imageUrl: String?,
    val readMoreUrl: String?
) : Parcelable

data class NewsData(
    val category: String?,
    val data: ArrayList<NewsContent>?
)

