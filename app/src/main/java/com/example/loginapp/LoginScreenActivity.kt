package com.example.loginapp

import android.content.Context
import android.content.Intent
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

            val email = 0
            val password = 1

            val loginButton = findViewById<Button>(R.id.loginButtonView)
            val emailField = findViewById<EditText>(R.id.emailTextView)
            val passwordField = findViewById<EditText>(R.id.passwordTextView)

            loginButton.setOnClickListener {

                val loginClass = Login()
                val credentials = loginClass.getLoginDetails(emailField, passwordField)

                if (loginClass.checkIsEmpty(appContext, credentials)) {

                    if (credentials[email] == "sam@gmail.com" && credentials[password] == "password"){

                        changeToMainScreen(appContext, credentials)
                    }
                    else{
                        showErrorMessage()
                    }
                }

            }

            val signupTextField = findViewById<TextView>(R.id.signUpTextView)
            showSignUpTextView(signupTextField)
            signupTextField.setOnClickListener {
                println("signUp")
                Toast.makeText(this, "SIGNUP", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showSignUpTextView (signUpText: TextView) {

        /*
        Method sets the sign up text in main activity
         */

        val linkColor = Color.parseColor("#3ea2c7")
        val signUpValue = "Need new account? Sign Up"
        val spanSignUp = SpannableString(signUpValue)
        spanSignUp.setSpan(ForegroundColorSpan(linkColor), 18, 25, SpannableString.SPAN_EXCLUSIVE_INCLUSIVE)
        signUpText.text = spanSignUp

    }

    private fun changeToMainScreen (activityContext: Context, loginCredentials: Array<String>) {
        /*
        Method to switch to home screen
         */

        val loginSharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
        val loginEditor = loginSharedPreferences.edit()


        loginEditor.putString("email", loginCredentials[0])
        loginEditor.putString("password", loginCredentials[1])
        loginEditor.apply()

        val activityIntent = Intent(activityContext, HomeScreenActivity::class.java)
        startActivity(activityIntent)
    }

    private fun showErrorMessage() {

        /*
        Method sets the error TextView
         */
        val error = findViewById<TextView>(R.id.errorTextView)

        if (error.visibility == View.VISIBLE){
            error.visibility = View.INVISIBLE
        }
        else{
            error.visibility = View.VISIBLE
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }

}

