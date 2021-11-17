package com.example.loginapp

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentInflater =  inflater.inflate(R.layout.fragment_login, container, false)



        return fragmentInflater
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val forgotPasswordTextView = view.findViewById<TextView>(R.id.forgotPasswordTextView)
        val signUpText =  view.findViewById<TextView>(R.id.signUpTextView)
        showSignUpText(signUpText)

        signUpText.setOnClickListener {
            val signUpTransaction = parentFragmentManager.beginTransaction()

            signUpTransaction.apply {
                replace(R.id.fragmentContainer, SignUpFragment())
                addToBackStack(null)
                commit()
            }
        }

        forgotPasswordTextView.setOnClickListener {
            val forgotPasswordFragment = ForgotPasswordFragment()
            val fragmentTransaction = parentFragmentManager.beginTransaction()

            fragmentTransaction.apply {
                replace(R.id.fragmentContainer, forgotPasswordFragment)
                addToBackStack("forgotPasswordFragmrnt")
                setReorderingAllowed(true)
                commit()
            }

        }
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