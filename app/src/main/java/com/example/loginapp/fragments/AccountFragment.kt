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
import com.example.loginapp.activities.LoginScreenActivity
import com.example.loginapp.R
import com.example.loginapp.databinding.FragmentAccountBinding


class AccountFragment() : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences

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

        sharedPreferences = requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE)
        accountFragmentBinding.profileImage.setImageResource(R.drawable.profile)
        accountFragmentBinding.userName.text = sharedPreferences.getString("username", "No Username")
        accountFragmentBinding.loginButtonView.setOnClickListener { showLogoutDialog()}

    }

    private fun showLogoutDialog(){

        val logoutConfirmAlert = AlertDialog.Builder(requireActivity())
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