package com.example.loginapp.utils

import android.content.SharedPreferences
import android.util.Log
import com.example.loginapp.model.Articles
import com.example.loginapp.model.NewsContent
import com.google.gson.Gson

class EditPreference(private val preferences: SharedPreferences) {

    fun getPreference(): MutableList<Articles> {
        val favJson = preferences.getString("Favourite", null)
        Log.d("json", favJson.toString())
        return Gson().fromJson(favJson, Array<Articles>::class.java).toMutableList()
    }

    fun addPreference(favNews: MutableList<Articles>) {
        val preferencesEditor = preferences.edit()
        val json = Gson().toJson(favNews)
        preferencesEditor.putString("Favourite", json)
        preferencesEditor.apply()
    }

    fun checkPreferenceExist(): Boolean {
        return preferences.contains("Favourite")
    }
}