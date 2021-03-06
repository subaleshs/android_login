package com.example.loginapp.utils

import android.text.TextUtils
import android.util.Patterns
import com.google.android.material.textfield.TextInputLayout


class Login(email: String, password: String) {

    private var userEmail = email
    private var userPassword = password


    fun checkLoginField(
        emailTextInputLayout: TextInputLayout,
        passwordTextInputLayout: TextInputLayout
    ): Boolean {

        /*
        Method to check for correct input format and for empty fields.

        returns Boolean: value depicting if any of the issue is present
         */

        if (TextUtils.isEmpty(userEmail) && TextUtils.isEmpty(userPassword)) {


            emailTextInputLayout.error = "Valid Email Required."
            passwordTextInputLayout.error = "Valid Password Required."
            return false
        } else if (TextUtils.isEmpty(userEmail) || !Patterns.EMAIL_ADDRESS.matcher(userEmail)
                .matches()
        ) {

            emailTextInputLayout.error = "Valid Email Required."
            return false
        } else if (TextUtils.isEmpty(userPassword)) {

            passwordTextInputLayout.error = "Valid Password Required"
            return false

        }

        return true
    }

}