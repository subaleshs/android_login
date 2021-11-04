package com.example.loginapp

import android.content.Context
import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast

class Login{

    fun getLoginDetails(emailField: EditText, passwordField: EditText): Array<String> {

        /*
        Method to retrieve login credentials entered by the user

        return: unit - Void function
         */


        val email = emailField.text.toString()
        val password = passwordField.text.toString()

        return arrayOf(email, password)
    }

    fun checkIsEmpty(context: Context, credentials: Array<String>): Boolean {

        /*
        Method to check for correct input format and for empty fields.

        returns Boolean: value depicting if any of the issue is present
         */

        val email = 0
        val password = 1

        val emptyFieldToast: Toast

        if (TextUtils.isEmpty(credentials[email]) && TextUtils.isEmpty(credentials[password])){

            emptyFieldToast = Toast.makeText(context, "Valid Email and Password Required.", Toast.LENGTH_LONG)
            emptyFieldToast.show()
            return false
        }
        else if (TextUtils.isEmpty(credentials[email]) || !Patterns.EMAIL_ADDRESS.matcher(credentials[email]).matches()) {

            emptyFieldToast = Toast.makeText(context, "Valid Email Required.", Toast.LENGTH_LONG)
            emptyFieldToast.show()
            return false
        }
        else if (TextUtils.isEmpty(credentials[password])){

            emptyFieldToast = Toast.makeText(context, "Valid Password Required", Toast.LENGTH_SHORT)
            emptyFieldToast.show()
            return false

        }

        return true
    }

}