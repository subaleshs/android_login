package com.example.loginapp.repositories

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.View
import com.example.loginapp.R
import com.example.loginapp.network.FirebaseAuthentication
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
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

//    fun userLogin(email: String, password: String) {
//        firebaseAuthInstance.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    onAuthSuccess?.invoke(firebaseAuthInstance.currentUser)
//                } else {
//                    onAuthFail?.invoke(firebaseAuthInstance.currentUser)
//                }
//            }
//    }

    fun userLogin(email: String, password: String): Task<AuthResult> = firebaseAuthInstance.signInWithEmailAndPassword(email, password)

    fun userSignUp(email: String, password: String): Task<AuthResult> = firebaseAuthInstance.createUserWithEmailAndPassword(email, password)

    fun sendResetPasswordMail(email: String): Task<Void> = firebaseAuthInstance.sendPasswordResetEmail(email)

    fun userLogOUt() = firebaseAuthInstance.signOut()

    fun currentUser() = firebaseAuthInstance.currentUser

}