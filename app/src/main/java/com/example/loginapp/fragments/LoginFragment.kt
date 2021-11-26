package com.example.loginapp.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.loginapp.HomeScreenActivity
import com.example.loginapp.Login
import com.example.loginapp.R
import com.example.loginapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var loginFragmentBinding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        loginFragmentBinding = FragmentLoginBinding.inflate(inflater, container, false)

        return loginFragmentBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        loginFragmentBinding.emailTextView.addTextChangedListener { loginFragmentBinding.emailTextInputLayout.error = null }
        loginFragmentBinding.passwordTextView.addTextChangedListener { loginFragmentBinding.passwordTextInputLayout.error = null }

        showSignUpText(loginFragmentBinding.signUpTextView)

        loginFragmentBinding.loginButtonView.setOnClickListener {

            val userEmailAddress = loginFragmentBinding.emailTextView.text.toString()
            val userPassword = loginFragmentBinding.passwordTextView.text.toString()

            val loginClass = Login(userEmailAddress, userPassword)

            if (loginClass.checkLoginField(loginFragmentBinding.emailTextInputLayout, loginFragmentBinding.passwordTextInputLayout)) {
                if (loginClass.authenticateUser(loginFragmentBinding.emailTextInputLayout, loginFragmentBinding.passwordTextInputLayout)) {
                    changeToHomeScreen( userEmailAddress, userPassword)
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

    private fun changeToHomeScreen (emailAddress:String, password: String) {
        /*
        Method to switch to home screen
         */

        val loginSharedPreferences = activity?.getSharedPreferences("user",
            AppCompatActivity.MODE_PRIVATE
        )
        val loginEditor = loginSharedPreferences?.edit()
        loginEditor?.putString("email", emailAddress)
        loginEditor?.putString("password", password)
        loginEditor?.apply()

        val activityIntent = Intent(activity, HomeScreenActivity::class.java)
        startActivity(activityIntent)
        activity?.finish()
    }

    
    private fun showSignUpText (signUpTextView: TextView) {

        /*
        Method sets the sign up text in main activity
         */

        val linkColor = Color.parseColor("#3ea2c7")
        val signUpValue = "Need new account? Sign Up"
        val spanSignUp = SpannableString(signUpValue)
        spanSignUp.setSpan(ForegroundColorSpan(linkColor), 18, 25, SpannableString.SPAN_EXCLUSIVE_INCLUSIVE)
        signUpTextView.text = spanSignUp

    }
}