package com.example.loginapp.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.example.loginapp.activities.HomeScreenActivity
import com.example.loginapp.Login
import com.example.loginapp.NetworkChecks
import com.example.loginapp.R
import com.example.loginapp.databinding.FragmentLoginBinding
import com.example.loginapp.repositories.AuthRepository
import java.lang.NullPointerException

class LoginFragment : Fragment() {

    private lateinit var loginFragmentBinding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        loginFragmentBinding = FragmentLoginBinding.inflate(inflater, container, false)

        return loginFragmentBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val auth = AuthRepository()

        loginFragmentBinding.progressBarView.visibility = View.INVISIBLE
        loginFragmentBinding.loginErrorText.visibility = View.INVISIBLE
        loginFragmentBinding.emailTextView.addTextChangedListener {
            loginFragmentBinding.emailTextInputLayout.error = null
            loginFragmentBinding.loginErrorText.visibility = View.INVISIBLE
        }
        loginFragmentBinding.passwordTextView.addTextChangedListener {
            loginFragmentBinding.passwordTextInputLayout.error = null
            loginFragmentBinding.loginErrorText.visibility = View.INVISIBLE
        }

        showSignUpText(loginFragmentBinding.signUpTextView)

        loginFragmentBinding.loginButtonView.setOnClickListener {

            if (NetworkChecks.isNetworkConnected(activity)) {
                val userEmailAddress = loginFragmentBinding.emailTextView.text.toString()
                val userPassword = loginFragmentBinding.passwordTextView.text.toString()

                val loginClass = Login(userEmailAddress, userPassword)

                if (loginClass.checkLoginField(
                        loginFragmentBinding.emailTextInputLayout,
                        loginFragmentBinding.passwordTextInputLayout
                    )
                ) {
                    loginFragmentBinding.progressBarView.visibility = View.VISIBLE
                    auth.userLogin(userEmailAddress, userPassword)
                }

                auth.onAuthSuccess = {
                    loginFragmentBinding.progressBarView.visibility = View.INVISIBLE
                    changeToHomeScreen()
                }

                auth.onAuthFail = {
                    loginFragmentBinding.progressBarView.visibility = View.INVISIBLE
                    loginFragmentBinding.loginErrorText.visibility = View.VISIBLE
                    loginFragmentBinding.emailTextInputLayout.error = " "
                    loginFragmentBinding.passwordTextInputLayout.error = " "
                }
            } else {
                context?.let {
                    AlertDialog.Builder(it).setTitle(R.string.no_internet)
                        .setMessage(R.string.no_internet_message)
                        .setPositiveButton(android.R.string.ok) { _, _ -> }
                        .setIcon(android.R.drawable.ic_dialog_alert).show()
                }

            }
        }

        loginFragmentBinding.signUpTextView.setOnClickListener {
            val signUpFragmentTransaction = parentFragmentManager.beginTransaction()

            signUpFragmentTransaction.apply {
                replace(R.id.fragmentContainer, SignUpFragment())
                addToBackStack(null)
                commit()
            }
        }

        loginFragmentBinding.forgotPasswordTextView.setOnClickListener {
            val resetPasswordFragment = PasswordResetFragment()
            val fragmentTransaction = parentFragmentManager.beginTransaction()

            fragmentTransaction.apply {
                replace(R.id.fragmentContainer, resetPasswordFragment)
                addToBackStack("forgotPasswordFragment")
                setReorderingAllowed(true)
                commit()
            }

        }
    }

    private fun changeToHomeScreen() {
        /**
         * Method to switch to home screen
         */
        val activityIntent = Intent(activity, HomeScreenActivity::class.java)
        startActivity(activityIntent)
        activity?.finish()
    }


    private fun showSignUpText(signUpTextView: TextView) {

        /**
         * Method sets the sign up text in main activity
         */

        val linkColor = Color.parseColor("#3ea2c7")
        val signUpValue = getString(R.string.signup_message)
        val spanSignUp = SpannableString(signUpValue)
        spanSignUp.setSpan(
            ForegroundColorSpan(linkColor),
            18,
            25,
            SpannableString.SPAN_EXCLUSIVE_INCLUSIVE
        )
        signUpTextView.text = spanSignUp

    }
}