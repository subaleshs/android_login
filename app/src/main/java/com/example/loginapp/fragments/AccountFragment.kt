package com.example.loginapp.fragments

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.loginapp.HomeScreenActivity
import com.example.loginapp.LoginScreenActivity
import com.example.loginapp.R
import com.example.loginapp.databinding.FragmentAccountBinding


class AccountFragment(private val sharedPreferences: SharedPreferences, private val activity: Context) : Fragment() {

    private lateinit var accountFragmentBinding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        accountFragmentBinding = FragmentAccountBinding.inflate(inflater, container, false)

        return  accountFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        accountFragmentBinding.profileImage.setImageResource(R.drawable.profile)
        accountFragmentBinding.userName.text = sharedPreferences.getString("username", "No Username")
        accountFragmentBinding.loginButtonView.setOnClickListener { showLogoutDialog()}

    }

    private fun showLogoutDialog(){

        val logoutConfirmAlert = AlertDialog.Builder(activity)
        logoutConfirmAlert.setMessage("Press Confirm to Logout")
        logoutConfirmAlert.setPositiveButton("Confirm"){ _, _ -> logOut() }
        logoutConfirmAlert.setNegativeButton("Cancel"){ dialog, _ -> dialog.dismiss() }
        logoutConfirmAlert.show()
    }

    private fun logOut() {
        val loginDetailEditor = sharedPreferences.edit()
        loginDetailEditor.clear()
        loginDetailEditor.apply()

        val activityIntent = Intent(activity, LoginScreenActivity::class.java)
        startActivity(activityIntent)
    }

}