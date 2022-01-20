package com.example.loginapp.repositories

import com.example.loginapp.network.FirebaseAuthentication
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult


class AuthRepository {

    private val firebaseAuthInstance = FirebaseAuthentication.getFirebaseAuthInstance()

    fun userLogin(email: String, password: String): Task<AuthResult> = firebaseAuthInstance.signInWithEmailAndPassword(email, password)

    fun userSignUp(email: String, password: String): Task<AuthResult> = firebaseAuthInstance.createUserWithEmailAndPassword(email, password)

    fun sendResetPasswordMail(email: String): Task<Void> = firebaseAuthInstance.sendPasswordResetEmail(email)

    fun userLogOUt() = firebaseAuthInstance.signOut()

    fun currentUser() = firebaseAuthInstance.currentUser

}