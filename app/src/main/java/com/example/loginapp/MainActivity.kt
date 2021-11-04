package com.example.loginapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appContext = applicationContext
        val email = 0
        val password = 1

        val loginButton = findViewById<Button>(R.id.loginButton)
        val emailField = findViewById<EditText>(R.id.emailField)
        val passwordField = findViewById<EditText>(R.id.passwordField)

        val pref = getSharedPreferences("user", MODE_PRIVATE)
        println(pref.contains("email"))

        loginButton.setOnClickListener {

            val loginClass = Login()
            val credentials = loginClass.getLoginDetails(emailField, passwordField)

            if (loginClass.checkIsEmpty(appContext, credentials)) {

//                for (i in 0 until (credentials.size)) {
//                    println(credentials[i])
//                }

                if (credentials[email] == "sam@gmail.com" && credentials[password] == "password"){

                    homeScreen(appContext, credentials)
                }
                else{
                    errorMessage()
//                    error.visibility = View.VISIBLE
                }
            }

        }

        val loginDetailsPreferences = getSharedPreferences("user", MODE_PRIVATE)
        if (loginDetailsPreferences.contains("email") && loginDetailsPreferences.contains("password")){

            val activityIntent = Intent(appContext, HomeScreen::class.java)
            startActivity(activityIntent)
        }
        val linkColor = Color.parseColor("#3ea2c7")

        val signupTextField = findViewById<TextView>(R.id.signUp)

        val signUpText = "Need new account? Sign Up"

        val spanSignUp = SpannableString(signUpText)

        spanSignUp.setSpan(ForegroundColorSpan(linkColor), 18, 25, SpannableString.SPAN_EXCLUSIVE_INCLUSIVE)

        signupTextField.text = spanSignUp

        signupTextField.setOnClickListener {
            println("signUp")
            Toast.makeText(this, "SIGNUP", Toast.LENGTH_SHORT).show()
        }
    }

    private fun homeScreen(activityContext: Context, loginCredentials: Array<String>) {
        /*
        Method to switch to home screen
         */

        val loginSharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
        val loginEditor = loginSharedPreferences.edit()


        loginEditor.putString("email", loginCredentials[0])
        loginEditor.putString("password", loginCredentials[1])
        loginEditor.apply()

        val activityIntent = Intent(activityContext, HomeScreen::class.java)
//        activityIntent.putExtra("username", loginCredentials[0].split("@")[0])
        startActivity(activityIntent)
    }

    private fun errorMessage() {

        /*
        Method sets the error TextView
         */
        val error = findViewById<TextView>(R.id.errorText)

        if (error.visibility == View.VISIBLE){
            error.visibility = View.INVISIBLE
        }
        else{
            error.visibility = View.VISIBLE
        }
    }





}

