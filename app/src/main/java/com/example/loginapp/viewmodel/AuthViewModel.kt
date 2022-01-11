package com.example.loginapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.loginapp.repositories.AuthRepository
import com.google.firebase.auth.FirebaseUser

class AuthViewModel: ViewModel() {

    private val userViewModel = MutableLiveData<FirebaseUser?>()
    private val userLoggedOut = MutableLiveData<Boolean>()
    private val exceptionMessageLiveData = MutableLiveData<String?>()
    private val authenticationRepository = AuthRepository()

    fun getUserViewModel(): MutableLiveData<FirebaseUser?> {
        return userViewModel
    }

    fun getExceptionMessage(): MutableLiveData<String?> {
        return exceptionMessageLiveData
    }

    fun logIn(userEmail: String, userPassword: String) {
        authenticationRepository.userLogin(userEmail,userPassword)
        authenticationRepository.onAuthSuccess = {
            userViewModel.postValue(it)
        }
        authenticationRepository.onAuthFail = {
            userViewModel.postValue(it)

        }
    }

    fun signUp(userEmail: String, userPassword: String) {
        authenticationRepository.userSignUp(userEmail,userPassword)
        authenticationRepository.onSuccess = {
            userViewModel.postValue(it)
        }
        authenticationRepository.onFailure = {
            userViewModel.postValue(it)

        }
    }

    fun resetPassword(email: String) {
        authenticationRepository.sendResetPasswordMail(email)
        authenticationRepository.onMailSuccess = {
            exceptionMessageLiveData.postValue(it)
        }
        authenticationRepository.onMailFailure = {
            exceptionMessageLiveData.postValue(it)
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return authenticationRepository.currentUser()
    }

    fun logOut() = userLoggedOut.postValue(true)
}