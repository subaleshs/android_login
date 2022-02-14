package com.example.loginapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginapp.repositories.AuthRepository
import com.google.firebase.auth.FirebaseUser

class AuthViewModel : ViewModel() {

    private val userLiveData = MutableLiveData<FirebaseUser?>()
    private val userLoggedOut = MutableLiveData<Boolean>()
    private val exceptionMessageLiveData = MutableLiveData<String?>()
    private val authenticationRepository = AuthRepository()

    fun getUserLiveData(): MutableLiveData<FirebaseUser?> {
        return userLiveData
    }

    fun getExceptionMessage(): MutableLiveData<String?> {
        return exceptionMessageLiveData
    }

    fun logIn(userEmail: String, userPassword: String) {
        val authentication = authenticationRepository.userLogin(userEmail, userPassword)
        authentication.addOnCompleteListener {
            if (authentication.isSuccessful) {
                userLiveData.postValue(authenticationRepository.currentUser())
            } else {
                userLiveData.postValue(authenticationRepository.currentUser())
            }
        }
    }

    fun signUp(userEmail: String, userPassword: String) {
        val newUserRegistrationTask = authenticationRepository.userSignUp(userEmail, userPassword)
        newUserRegistrationTask.addOnCompleteListener {
            if (newUserRegistrationTask.isSuccessful) {
                userLiveData.postValue(authenticationRepository.currentUser())
            } else {
                exceptionMessageLiveData.postValue(newUserRegistrationTask.exception?.message)
            }
        }
    }

    fun resetPassword(email: String) {
        val resetPasswordTask = authenticationRepository.sendResetPasswordMail(email)
        resetPasswordTask.addOnCompleteListener {
            if (resetPasswordTask.isSuccessful) {
                exceptionMessageLiveData.postValue(null)
            } else {
                exceptionMessageLiveData.postValue(resetPasswordTask.exception?.message)
            }
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return authenticationRepository.currentUser()
    }

    fun logOut() {
        authenticationRepository.userLogOUt()
    }
}