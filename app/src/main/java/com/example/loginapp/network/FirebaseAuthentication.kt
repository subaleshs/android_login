package com.example.loginapp.network

import com.google.firebase.auth.FirebaseAuth

object FirebaseAuthentication {
    fun getFirebaseAuthInstance() = FirebaseAuth.getInstance()
}