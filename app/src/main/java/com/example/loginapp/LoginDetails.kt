package com.example.loginapp

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

class Login(email: String, password: String){

    private var userEmail = email
    private var userPassword = password


    fun checkIsEmpty(context: Context, emailTextInputLayout: TextInputLayout, passwordTextInputLayout: TextInputLayout): Boolean {

        /*
        Method to check for correct input format and for empty fields.

        returns Boolean: value depicting if any of the issue is present
         */

        val emptyFieldToast: Toast

        if (TextUtils.isEmpty(userEmail) && TextUtils.isEmpty(userPassword)){

            emailTextInputLayout.error = "Valid Email Required."
            passwordTextInputLayout.error = "Valid Password Required."

            emptyFieldToast = Toast.makeText(context, "Valid Email and Password Required.", Toast.LENGTH_LONG)
            emptyFieldToast.show()
            return false
        }
        else if (TextUtils.isEmpty(userEmail) || !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {

            emailTextInputLayout.error = "Valid Email Required."

            emptyFieldToast = Toast.makeText(context, "Valid Email Required.", Toast.LENGTH_LONG)
            emptyFieldToast.show()
            return false
        }
        else if (TextUtils.isEmpty(userPassword)){

            passwordTextInputLayout.error = "Valid Password Required"

            emptyFieldToast = Toast.makeText(context, "Valid Password Required", Toast.LENGTH_SHORT)
            emptyFieldToast.show()
            return false

        }

        return true
    }

    fun checkLoginCredentials(emailTextInputLayout: TextInputLayout, passwordTextInputLayout: TextInputLayout): Boolean{

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