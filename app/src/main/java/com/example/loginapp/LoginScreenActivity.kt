package com.example.loginapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LoginScreenActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val loginDetailsPreferences = getSharedPreferences("user", MODE_PRIVATE)
        if (loginDetailsPreferences.contains("email") && loginDetailsPreferences.contains("password")){

            val activityIntent = Intent(this, HomeScreenActivity::class.java)
            startActivity(activityIntent)
        }
        else{

            setContentView(R.layout.activity_login_screen)

        }

    }

    override fun onBackPressed() {
        val backStackCount = supportFragmentManager.backStackEntryCount

        if (backStackCount == 0){
            moveTaskToBack(true)
        }
        else{
            super.onBackPressed()
        }
    }


}

