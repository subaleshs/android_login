package com.example.loginapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginButton = findViewById<Button>(R.id.loginButton)

        val emailField = findViewById<EditText>(R.id.emailField)
        val passwordField = findViewById<EditText>(R.id.passwordField)

        loginButton.setOnClickListener {
//            var credntials = getLoginDetails(emailField, passwordField)


            if(checkIsEmpty(emailField, passwordField)){
                var credntials = getLoginDetails(emailField, passwordField)
            }

        }
    }

    private fun getLoginDetails(emailField: EditText, passwordField: EditText): Array<String> {

        /*
        Method to retrieve login credentials entered by the user

        return: unit - Void function
         */


        var email = emailField.text.toString()
        var password = passwordField.text.toString()
        println("Email = $email Pass = $password")

        return arrayOf(email, password)
    }

    private fun checkIsEmpty(emailField: EditText, passwordField: EditText): Boolean {

        var emptyFieldToast: Toast

        if (isEmpty(emailField.text.toString()) && isEmpty(passwordField.text.toString())){
            emptyFieldToast = Toast.makeText(baseContext, "Email and Password Field Required.", Toast.LENGTH_LONG)
            emptyFieldToast.show()
            return false
        }
        else if (isEmpty(emailField.text.toString())) {
            emptyFieldToast = Toast.makeText(baseContext, "Email Field Required.", Toast.LENGTH_LONG)
            emptyFieldToast.show()
            return false
        }
        else if (isEmpty(passwordField.text.toString())){

            emptyFieldToast = Toast.makeText(baseContext, "Password Field Required", Toast.LENGTH_SHORT)
            emptyFieldToast.show()
            return false

        }

        return true
    }

}