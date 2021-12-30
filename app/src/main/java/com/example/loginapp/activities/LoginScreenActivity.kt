package com.example.loginapp.activities

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.loginapp.databinding.ActivityLoginScreenBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.jar.Manifest

class LoginScreenActivity : AppCompatActivity() {

    private lateinit var loginActivityBinding: ActivityLoginScreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 111)
        }
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

