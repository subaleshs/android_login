package com.example.loginapp

import android.app.Activity
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity

class NetworkChecks {

    fun isNetworkConnected(activity: Activity?): Boolean {

        val connectivityManager = activity?.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager == null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val activeNetwork = connectivityManager.activeNetwork
                val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
                return networkCapabilities != null && networkCapabilities.hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_INTERNET)
            } else {
                @Suppress("DEPRECATION")
                return connectivityManager.activeNetworkInfo != null
            }
        }
       return false
    }
}