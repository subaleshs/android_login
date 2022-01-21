package com.example.loginapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

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
    val data: ArrayList<NewsContent>?,
    val success: Boolean,
    val error: String?
)

data class News(
    val status: String?,
    val code: String?,
    val message: String?,
    val totalResults: String?,
    val articles: ArrayList<Articles>?,
)

@Parcelize
data class Articles(
    val source: @RawValue Source,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
): Parcelable

data class Source(
    val id: String?,
    val name: String?
)
