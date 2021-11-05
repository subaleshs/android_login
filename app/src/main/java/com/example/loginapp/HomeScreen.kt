package com.example.loginapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class HomeScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

//        var userName = intent.getStringExtra("username")


        val loginSharedPreferences: SharedPreferences= getSharedPreferences("user", MODE_PRIVATE)
        var userName: String? = loginSharedPreferences.getString("email", null)

        if (userName != null){
            val textField = findViewById<TextView>(R.id.userNameText)
            textField.text = userName.split("@")[0]
        }

        if (loginSharedPreferences.getBoolean("autoLogoutBool", false)){
            timeCounter(loginSharedPreferences)
        }

        var logoutButton = findViewById<Button>(R.id.logoutButton)

        logoutButton.setOnClickListener {
            logOut(loginSharedPreferences)
        }



    }

    private fun timeCounter(sharedPreferences: SharedPreferences){
        val timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // do nothing
                val timerText = findViewById<TextView>(R.id.timer)
                timerText.text = "${millisUntilFinished/1000}"
            }

            override fun onFinish() {
                logOut(sharedPreferences)
            }
        }
        timer.start()
    }

    private fun logOut(sharedPreferences: SharedPreferences) {
        val loginDetailEditor = sharedPreferences.edit()
        loginDetailEditor.clear()
        loginDetailEditor.apply()
        finish()
    }
}