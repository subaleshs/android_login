package com.example.loginapp.repositories

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.View
import com.example.loginapp.R
import com.google.firebase.auth.FirebaseAuth

class AuthRepository {

    var onAuthSuccess: (() -> Unit)? = null
    var onSuccess: (() -> Unit)? = null
    var onMailSuccess: (() -> Unit)? = null
    var onAuthFail: (() -> Unit)? = null
    var onFailure: ((String?) -> Unit)? = null
    var onMailFailure: ((String?) -> Unit)? = null

    fun userLogin(email: String, password: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onAuthSuccess?.invoke()
                } else {
                    Log.d("f", "asdf")
                    onAuthFail?.invoke()
                }
            }
    }

    fun userSignUp(email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onSuccess?.invoke()

                } else {
                    it.exception?.message.let { it1 -> onFailure?.invoke(it1) }
                }
            }
    }

    fun passwordReset(email: String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onMailSuccess?.invoke()
                } else {
                    onMailFailure?.invoke(it.exception?.message)
                }
            }
    }

}