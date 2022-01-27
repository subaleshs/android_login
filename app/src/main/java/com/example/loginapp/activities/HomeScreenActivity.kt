package com.example.loginapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.loginapp.R
import com.example.loginapp.adapters.SwipeViewAdapter
import com.example.loginapp.databinding.ActivityHomeScreenBinding
import com.example.loginapp.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

class HomeScreenActivity : AppCompatActivity() {

    private lateinit var homeScreenActivityBinding: ActivityHomeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (FirebaseAuth.getInstance().currentUser == null) {

            val activityIntent = Intent(applicationContext, LoginScreenActivity::class.java)
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
            exitConfirm.setMessage(R.string.exit_dialog_message)
            exitConfirm.setPositiveButton(R.string.confirm) { _, _ -> finishAffinity() }
            exitConfirm.setNegativeButton(R.string.cancel) { dialog, _ -> dialog.cancel() }
            exitConfirm.show()
        } else {
            super.onBackPressed()
        }
    }

}