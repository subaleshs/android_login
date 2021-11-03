package com.example.loginapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val activityContext = applicationContext

        val loginButton = findViewById<Button>(R.id.loginButton)

        val emailField = findViewById<EditText>(R.id.emailField)
        val passwordField = findViewById<EditText>(R.id.passwordField)

        loginButton.setOnClickListener {
//            var credentials = getLoginDetails(emailField, passwordField)

//            val testToaast = Toast.makeText(this, "success", Toast.LENGTH_SHORT)
//            testToaast.show()

            val loginClass = Login()
            val credentials = loginClass.getLoginDetails(emailField, passwordField)

            if (loginClass.checkIsEmpty(activityContext, credentials)) {


                for (i in 0 until (credentials.size)) {
                    println(credentials[i])
                }
            }

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