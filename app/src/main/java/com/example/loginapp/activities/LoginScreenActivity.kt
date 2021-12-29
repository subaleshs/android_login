package com.example.loginapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.loginapp.databinding.ActivityLoginScreenBinding
import com.google.firebase.auth.FirebaseAuth

class LoginScreenActivity : AppCompatActivity() {

    private lateinit var loginActivityBinding: ActivityLoginScreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        if (FirebaseAuth.getInstance().currentUser != null) {

            val activityIntent = Intent(this, HomeScreenActivity::class.java)
            startActivity(activityIntent)
        } else {

            loginActivityBinding = ActivityLoginScreenBinding.inflate(layoutInflater)

            setContentView(loginActivityBinding.root)

        }

    }

    override fun onBackPressed() {
        val backStackCount = supportFragmentManager.backStackEntryCount

        if (backStackCount == 0) {
            moveTaskToBack(true)
        } else {
            super.onBackPressed()
        }
    }

}

