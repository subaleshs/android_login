package com.example.loginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

//        var userName = intent.getStringExtra("username")

        val loginSharedPreferences = getSharedPreferences("user", MODE_PRIVATE)

        var userName: String? = loginSharedPreferences.getString("email", null)

        if (userName != null){
            val textField = findViewById<TextView>(R.id.userNameText)
            textField.text = userName.split("@")[0]
        }


        var logoutButton = findViewById<Button>(R.id.logoutButton)

        logoutButton.setOnClickListener {

            val loginDetailEditor = loginSharedPreferences.edit()
            loginDetailEditor.clear()
            loginDetailEditor.apply()
            finish()
        }



    }
}