package com.example.loginapp.fragments

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
import com.example.loginapp.activities.LoginScreenActivity
import com.example.loginapp.R
import com.example.loginapp.databinding.FragmentAccountBinding
import com.google.firebase.auth.FirebaseAuth


class AccountFragment() : Fragment() {

    private lateinit var accountFragmentBinding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        accountFragmentBinding = FragmentAccountBinding.inflate(inflater, container, false)
        return accountFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val authFirebase = FirebaseAuth.getInstance()
        accountFragmentBinding.profileImage.setImageResource(R.drawable.profile)
        val email = authFirebase.currentUser?.email
        accountFragmentBinding.userName.text = email?.split('@')?.get(0) ?: "No Username"
        accountFragmentBinding.loginButtonView.setOnClickListener { showLogoutDialog() }

    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = "Account"
    }

    private fun showLogoutDialog() {

        val logoutConfirmAlert = AlertDialog.Builder(requireActivity())
        logoutConfirmAlert.setMessage("Press Confirm to Logout")
        logoutConfirmAlert.setPositiveButton("Confirm") { _, _ -> logOut() }
        logoutConfirmAlert.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
        logoutConfirmAlert.show()
    }

    private fun logOut() {
        FirebaseAuth.getInstance().signOut()
        val activityIntent = Intent(activity, LoginScreenActivity::class.java)
        startActivity(activityIntent)
    }

}