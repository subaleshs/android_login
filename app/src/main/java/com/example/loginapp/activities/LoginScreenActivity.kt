package com.example.loginapp.activities

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.loginapp.databinding.ActivityLoginScreenBinding
import com.example.loginapp.viewmodel.AuthViewModel


class LoginScreenActivity : AppCompatActivity() {

    private lateinit var loginActivityBinding: ActivityLoginScreenBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                111
            )
        }
        val viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        if (viewModel.getCurrentUser() != null) {

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

