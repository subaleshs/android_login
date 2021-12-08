package com.example.loginapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.loginapp.databinding.ActivityLoginScreenBinding

class LoginScreenActivity : AppCompatActivity(){

    private lateinit var loginActivityBinding: ActivityLoginScreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val loginDetailsPreferences = getSharedPreferences("user", MODE_PRIVATE)
        if (loginDetailsPreferences.contains("email") && loginDetailsPreferences.contains("password")){

            val activityIntent = Intent(this, HomeScreenActivity::class.java)
            startActivity(activityIntent)
        }
        else{

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

