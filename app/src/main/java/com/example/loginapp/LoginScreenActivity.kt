package com.example.loginapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout

class LoginScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val appContext = applicationContext
        val loginDetailsPreferences = getSharedPreferences("user", MODE_PRIVATE)

        if (loginDetailsPreferences.contains("email") && loginDetailsPreferences.contains("password")){

            val activityIntent = Intent(appContext, HomeScreenActivity::class.java)
            startActivity(activityIntent)
        }
        else{

            setContentView(R.layout.activity_login_screen)

            val fragmentTransacion = supportFragmentManager.beginTransaction()

            fragmentTransacion.apply {
                add(R.id.fragmentContainer, LoginFragment())
                commit()
            }

//            val loginButton = findViewById<Button>(R.id.loginButtonView)
//            val emailField = findViewById<EditText>(R.id.emailTextView)
//            val passwordField = findViewById<EditText>(R.id.passwordTextView)
//            val emailTextInputLayout = findViewById<TextInputLayout>(R.id.emailTextInputLayout)
//            val passwordTextInputLayout = findViewById<TextInputLayout>(R.id.passwordTextInputLayout)
//
//            val signupTextField = findViewById<TextView>(R.id.signUpTextView)
//            showSignUpText(signupTextField)
//            signupTextField.setOnClickListener {
//                println("signUp")
//                Toast.makeText(this, "SIGNUP", Toast.LENGTH_SHORT).show()
//            }
//
//
//            emailField.addTextChangedListener { emailTextInputLayout.error = null }
//            passwordField.addTextChangedListener { passwordTextInputLayout.error = null }

//            loginButton.setOnClickListener {
//                val userEmailAddress = emailField.text.toString()
//                val userPassword = passwordField.text.toString()
//
//                val loginClass = Login(userEmailAddress, userPassword)
//
//                if (loginClass.checkLoginField(emailTextInputLayout, passwordTextInputLayout)) {
//                    if (loginClass.authenticateUser(emailTextInputLayout, passwordTextInputLayout)){
//                        changeToHomeScreen(appContext, userEmailAddress, userPassword)
//                    }
//                }
//            }

        }

    }

    private fun changeToHomeScreen (activityContext: Context, emailAddress:String, password: String) {
        /*
        Method to switch to home screen
         */

        val loginSharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
        val loginEditor = loginSharedPreferences.edit()
        loginEditor.putString("email", emailAddress)
        loginEditor.putString("password", password)
        loginEditor.apply()

        val activityIntent = Intent(activityContext, HomeScreenActivity::class.java)
        startActivity(activityIntent)
    }
}

