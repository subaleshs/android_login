package com.example.loginapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.example.loginapp.R
import com.example.loginapp.adapters.SwipeViewAdapter
import com.example.loginapp.databinding.ActivityHomeScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging

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

            FirebaseMessaging.getInstance().token.addOnCompleteListener {
                if (!it.isComplete){
                    return@addOnCompleteListener
                }

                val token = it.result
                Log.d("newtoken", token)
            }
            val viewPager = homeScreenActivityBinding.viewPagerBottomNav
            viewPager.adapter = SwipeViewAdapter(supportFragmentManager, lifecycle)
            homeScreenActivityBinding.bottomNavigation.setOnItemSelectedListener {

                if (supportFragmentManager.backStackEntryCount >= 1) {
                    supportFragmentManager.popBackStack()
                }
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.setDisplayShowHomeEnabled(false)
                when (it.itemId) {
                    R.id.home -> {
                        supportActionBar?.setTitle(R.string.home_title)
                        viewPager.currentItem = 0
                    }
                    R.id.favorites -> {
                        supportActionBar?.setTitle(R.string.fav_title)
                        viewPager.currentItem = 1
                    }
                    R.id.account -> {
                        supportActionBar?.setTitle(R.string.account_title)
                        viewPager.currentItem = 2
                    }
                }
                true
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun onBackPressed() {

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
        if (supportActionBar?.title != getString(R.string.home_title)) {
            supportActionBar?.setTitle(R.string.home_title)
        }
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