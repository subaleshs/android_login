package com.example.loginapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.loginapp.activities.LoginScreenActivity
import com.example.loginapp.R
import com.example.loginapp.databinding.FragmentAccountBinding
import com.example.loginapp.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth


class AccountFragment : Fragment() {

    private lateinit var accountFragmentBinding: FragmentAccountBinding
    lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        accountFragmentBinding = FragmentAccountBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        return accountFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        accountFragmentBinding.profileImage.setImageResource(R.drawable.profile)
        val email = viewModel.getCurrentUser()?.email
        accountFragmentBinding.userName.text = email?.split('@')?.get(0) ?: "No Username"
        accountFragmentBinding.loginButtonView.setOnClickListener { showLogoutDialog() }

        accountFragmentBinding.favoritesButton.setOnClickListener {
            val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPagerBottomNav)
            viewPager.currentItem = 1
        }

    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.account_title)
    }

    private fun showLogoutDialog() {

        val logoutConfirmAlert = AlertDialog.Builder(requireActivity())
        logoutConfirmAlert.setMessage(R.string.logout_confirm)
        logoutConfirmAlert.setPositiveButton(R.string.confirm) { _, _ -> logOut() }
        logoutConfirmAlert.setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
        logoutConfirmAlert.show()
    }

    private fun logOut() {
        viewModel.logOut()
        val activityIntent = Intent(activity, LoginScreenActivity::class.java)
        startActivity(activityIntent)
    }

}