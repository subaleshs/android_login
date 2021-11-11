package com.example.loginapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loginSharedPreferences: SharedPreferences= getSharedPreferences("user", MODE_PRIVATE)
        val appContext = applicationContext

        if (!loginSharedPreferences.contains("email") && !loginSharedPreferences.contains("password")){

            val activityIntent = Intent(appContext, LoginScreen::class.java)
            startActivity(activityIntent)
        }
        else{
            setContentView(R.layout.activity_main)

//        var userName = intent.getStringExtra("username")



            val userName: String? = loginSharedPreferences.getString("email", null)

            if (userName != null){
                val textField = findViewById<TextView>(R.id.userNameText)
                textField.text = userName.split("@")[0]
            }

            if (loginSharedPreferences.getBoolean("autoLogoutBool", false)){
                setTimeCounter(loginSharedPreferences)
            }

            val logoutButton = findViewById<Button>(R.id.logoutButton)

            logoutButton.setOnClickListener {
                showLogoutConfirm(loginSharedPreferences)
            }
        }

    }

    private fun setTimeCounter(sharedPreferences: SharedPreferences){
        val timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // do nothing
                val timerText = findViewById<TextView>(R.id.timer)
                timerText.text = "${millisUntilFinished/1000}"
            }

            override fun onFinish() {
                showLogoutConfirm(sharedPreferences)
            }
        }
        timer.start()
    }

    private fun logOut(sharedPreferences: SharedPreferences) {
        val loginDetailEditor = sharedPreferences.edit()
        loginDetailEditor.clear()
        loginDetailEditor.apply()
        val activityIntent = Intent(applicationContext, LoginScreen::class.java)
        startActivity(activityIntent)
    }

    private fun showLogoutConfirm(sharedDetails: SharedPreferences){

        val logoutConfirmAlert = AlertDialog.Builder(this)

        logoutConfirmAlert.setMessage("Press Confirm to Logout")

        logoutConfirmAlert.setPositiveButton("Confirm"){
                _, _ -> logOut(sharedDetails)
        }
        logoutConfirmAlert.setNegativeButton("Cancel"){
                dialog, _ -> dialog.dismiss()
        }
        logoutConfirmAlert.show()
    }

    override fun onBackPressed() {

        val exitConfirm = AlertDialog.Builder(this)

        exitConfirm.setMessage("Do you want to exit?")

        exitConfirm.setPositiveButton("Confirm"){
                _, _ -> finishAffinity()
        }

        exitConfirm.setNegativeButton("Cancel"){
                dialog, _ -> dialog.cancel()
        }

        exitConfirm.show()
    }

}