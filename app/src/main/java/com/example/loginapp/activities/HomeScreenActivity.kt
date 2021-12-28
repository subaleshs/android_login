package com.example.loginapp.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.loginapp.R
import com.example.loginapp.adapters.SwipeViewAdapter
import com.example.loginapp.databinding.ActivityHomeScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeScreenActivity : AppCompatActivity() {

    private lateinit var homeScreenActivityBinding: ActivityHomeScreenBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loginSharedPreferences: SharedPreferences = getSharedPreferences("user", MODE_PRIVATE)
        val appContext = applicationContext
        auth = Firebase.auth


        if (!loginSharedPreferences.contains("email") && !loginSharedPreferences.contains("password")) {

            val activityIntent = Intent(appContext, LoginScreenActivity::class.java)
            startActivity(activityIntent)
        } else {

            homeScreenActivityBinding = ActivityHomeScreenBinding.inflate(layoutInflater)
            setContentView(homeScreenActivityBinding.root)

            val viewPager = homeScreenActivityBinding.viewPagerBottomNav
            viewPager.adapter = SwipeViewAdapter(supportFragmentManager, lifecycle)
            homeScreenActivityBinding.bottomNavigation.setOnItemSelectedListener {

                if (supportFragmentManager.backStackEntryCount >= 1) {
                    supportFragmentManager.popBackStack()
                }
                when (it.itemId) {
                    R.id.home -> {
                        viewPager.currentItem = 0
                    }
                    R.id.favorites -> {
                        viewPager.currentItem = 1
                    }
                    R.id.account -> {
                        viewPager.currentItem = 2
                    }
                }
                true
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount < 1) {
            val exitConfirm = AlertDialog.Builder(this)
            exitConfirm.setMessage("Do you want to exit?")
            exitConfirm.setPositiveButton("Confirm") { _, _ -> finishAffinity() }
            exitConfirm.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            exitConfirm.show()
        } else {
            super.onBackPressed()
        }
    }

}