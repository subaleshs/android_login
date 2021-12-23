package com.example.loginapp

import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login(email: String, password: String){

    private var userEmail = email
    private var userPassword = password


    fun checkLoginField(emailTextInputLayout: TextInputLayout, passwordTextInputLayout: TextInputLayout): Boolean {

        /*
        Method to check for correct input format and for empty fields.

        returns Boolean: value depicting if any of the issue is present
         */

        if (TextUtils.isEmpty(userEmail) && TextUtils.isEmpty(userPassword)){


            emailTextInputLayout.error = "Valid Email Required."
            passwordTextInputLayout.error = "Valid Password Required."
            return false
        }
        else if (TextUtils.isEmpty(userEmail) || !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {

            emailTextInputLayout.error = "Valid Email Required."
            return false
        }
        else if (TextUtils.isEmpty(userPassword)){

            passwordTextInputLayout.error = "Valid Password Required"
            return false

        }

        return true
    }

    fun authenticateUser(emailTextInputLayout: TextInputLayout, passwordTextInputLayout: TextInputLayout): Boolean{

        /*
        Method checks user email and password to authenticate the user

        returns Boolean: true on successful authentication else false.
         */

        FirebaseAuth.getInstance().signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Log.d("login", task::class.simpleName.toString())
                }
                else{
                    Log.d("fail", task.toString())
                }
            }
        return if (userEmail == "sam@gmail.com" ){

            if (userPassword == "password"){
                true
            } else{
                passwordTextInputLayout.error = "Incorrect Password."
                false
            }
        } else{
            emailTextInputLayout.error = "Incorrect Email."
            false
        }
    }

}