package com.example.loginapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        var mailAddress = intent.getStringExtra("email")
        println(mailAddress)

        val textField = findViewById<TextView>(R.id.textView)
        textField.text = mailAddress
    }
}