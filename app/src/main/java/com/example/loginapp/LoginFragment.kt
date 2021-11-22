package com.example.loginapp

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
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val forgotPasswordTextView = view.findViewById<TextView>(R.id.forgotPasswordTextView)
        val signUpText =  view.findViewById<TextView>(R.id.signUpTextView)
        val loginButton = view.findViewById<Button>(R.id.loginButtonView)
        val emailField = view.findViewById<TextInputEditText>(R.id.emailTextView)
        val passwordField = view.findViewById<TextInputEditText>(R.id.passwordTextView)
        val emailTextInputLayout = view.findViewById<TextInputLayout>(R.id.emailTextInputLayout)
        val passwordTextInputLayout = view.findViewById<TextInputLayout>(R.id.passwordTextInputLayout)

        emailField.addTextChangedListener { emailTextInputLayout.error = null }
        passwordField.addTextChangedListener { passwordTextInputLayout.error = null }

        showSignUpText(signUpText)

        loginButton.setOnClickListener {

            val userEmailAddress = emailField.text.toString()
            val userPassword = passwordField.text.toString()

            val loginClass = Login(userEmailAddress, userPassword)

            if (loginClass.checkLoginField(emailTextInputLayout, passwordTextInputLayout)) {
                if (loginClass.authenticateUser(emailTextInputLayout, passwordTextInputLayout)) {
                    changeToHomeScreen( userEmailAddress, userPassword)
                }
            }
        }

        signUpText.setOnClickListener {
            val signUpFragmentTransaction = parentFragmentManager.beginTransaction()

            signUpFragmentTransaction.apply {
                replace(R.id.fragmentContainer, SignUpFragment())
                addToBackStack(null)
                commit()
            }
        }

        forgotPasswordTextView.setOnClickListener {
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