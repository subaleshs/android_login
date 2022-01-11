package com.example.loginapp.repositories

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.View
import com.example.loginapp.R
import com.example.loginapp.network.FirebaseAuthentication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthRepository {

    var onAuthSuccess: ((FirebaseUser?) -> Unit)? = null
    var onSuccess: ((FirebaseUser?) -> Unit)? = null
    var onMailSuccess: ((String?) -> Unit)? = null
    var onAuthFail: ((FirebaseUser?) -> Unit)? = null
    var onFailure: ((FirebaseUser?) -> Unit)? = null
    var onMailFailure: ((String?) -> Unit)? = null
    private val firebaseAuthInstance = FirebaseAuthentication.getFirebaseAuthInstance()

    fun userLogin(email: String, password: String) {
        firebaseAuthInstance.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onAuthSuccess?.invoke(firebaseAuthInstance.currentUser)
                } else {
                    onAuthFail?.invoke(firebaseAuthInstance.currentUser)
                }
            }
    }

    fun userSignUp(email: String, password: String) {
        firebaseAuthInstance.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onSuccess?.invoke(firebaseAuthInstance.currentUser)

                } else {
//                    it.exception?.message.let { it1 -> onFailure?.invoke(it1) }
                    onFailure?.invoke(firebaseAuthInstance.currentUser)
                }
            }
    }

    fun sendResetPasswordMail(email: String) {
        firebaseAuthInstance.sendPasswordResetEmail(email)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onMailSuccess?.invoke(it.exception?.message)
                } else {
                    onMailFailure?.invoke(it.exception?.message)
                }
            }
    }

    fun userLogOUt() {
        firebaseAuthInstance.signOut()
    }

    fun currentUser() = firebaseAuthInstance.currentUser

}