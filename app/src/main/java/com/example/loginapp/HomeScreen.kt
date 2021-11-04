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

        var userName = intent.getStringExtra("username")


        val textField = findViewById<TextView>(R.id.userNameText)
        textField.text = userName

        var logoutButton = findViewById<Button>(R.id.logoutButton)

        logoutButton.setOnClickListener {
            val mainActivityIntent: Intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainActivityIntent)
        }
    }
}