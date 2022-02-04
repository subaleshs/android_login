package com.example.loginapp.utils

import android.os.Build

object APILevelCheck {

    fun isAPIGreaterThan28(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    }
 }