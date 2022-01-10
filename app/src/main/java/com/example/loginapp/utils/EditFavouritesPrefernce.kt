package com.example.loginapp.utils

import android.content.SharedPreferences
import android.util.Log
import com.example.loginapp.model.NewsContent
import com.google.gson.Gson

class SavePreference(private val preferences: SharedPreferences) {

    fun getPreference(): MutableList<NewsContent> {
        val favJson = preferences.getString("Favourite", null)
        Log.d("json", favJson.toString())
        return Gson().fromJson(favJson, Array<NewsContent>::class.java).toMutableList()
    }

    fun addPreference(favNews: MutableList<NewsContent>) {
        val preferencesEditor = preferences.edit()
        val json = Gson().toJson(favNews)
        preferencesEditor.putString("Favourite", json)
        preferencesEditor.apply()
    }
}