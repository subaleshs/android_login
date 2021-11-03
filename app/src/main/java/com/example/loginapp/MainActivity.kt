package com.example.loginapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.TextUtils.isEmpty
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val activityContext = applicationContext
        val email = 0
        val password = 1

        val loginButton = findViewById<Button>(R.id.loginButton)

        val emailField = findViewById<EditText>(R.id.emailField)
        val passwordField = findViewById<EditText>(R.id.passwordField)

        loginButton.setOnClickListener {

            val error = findViewById<TextView>(R.id.errorText)

            if (error.visibility == View.VISIBLE){
                error.visibility = View.INVISIBLE
            }
//            else{
//                error.visibility = View.VISIBLE
//            }

            val loginClass = Login()
            val credentials = loginClass.getLoginDetails(emailField, passwordField)

            if (loginClass.checkIsEmpty(activityContext, credentials)) {


                for (i in 0 until (credentials.size)) {
                    println(credentials[i])
                }

                if (credentials[email] == "sam@gmail.com" && credentials[password] == "password"){
                    val activityIntent: Intent = Intent(applicationContext, HomeScreen::class.java)
                    activityIntent.putExtra("email",credentials[email])
                    startActivity(activityIntent)
                }
                else{
                    error.visibility = View.VISIBLE
                }
            }

        }

        val linkColor = Color.parseColor("#3ea2c7")

        val signupTextField = findViewById<TextView>(R.id.signUp)

        val signUpText: String = "Need new account? Sign Up"

        val spanSignUp = SpannableString(signUpText)

        spanSignUp.setSpan(ForegroundColorSpan(linkColor), 18, 25, SpannableString.SPAN_EXCLUSIVE_INCLUSIVE)

        signupTextField.text = spanSignUp

        signupTextField.setOnClickListener {
            println("signUp")
            val signupToast = Toast.makeText(this, "HEY", Toast.LENGTH_SHORT)
        }
    }


}

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

        if (isEmpty(credentials[email]) && isEmpty(credentials[password]) ){

            emptyFieldToast = Toast.makeText(context, "Valid Email and Password Required.", Toast.LENGTH_LONG)
            emptyFieldToast.show()
            return false
        }
        else if (isEmpty(credentials[email]) || !Patterns.EMAIL_ADDRESS.matcher(credentials[email]).matches()) {

            emptyFieldToast = Toast.makeText(context, "Valid Email Required.", Toast.LENGTH_LONG)
            emptyFieldToast.show()
            return false
        }
        else if (isEmpty(credentials[password])){

            emptyFieldToast = Toast.makeText(context, "Valid Password Required", Toast.LENGTH_SHORT)
            emptyFieldToast.show()
            return false

        }

        return true
    }

}