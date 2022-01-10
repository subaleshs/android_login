package com.example.loginapp.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.example.loginapp.utils.NetworkChecks
import com.example.loginapp.R
import com.example.loginapp.activities.HomeScreenActivity
import com.example.loginapp.databinding.FragmentSignupBinding
import com.example.loginapp.repositories.AuthRepository

class SignUpFragment : Fragment() {

    private lateinit var signUpFragmentBinding: FragmentSignupBinding
    val auth = AuthRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        signUpFragmentBinding = FragmentSignupBinding.inflate(inflater, container, false)
        return signUpFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpFragmentBinding.progressBarView.visibility = View.INVISIBLE
        signUpFragmentBinding.emailTextView.addTextChangedListener {
            signUpFragmentBinding.emailTextInputLayout.error = null
        }
        signUpFragmentBinding.passwordTextView.addTextChangedListener {
            if (signUpFragmentBinding.passwordTextView.text.toString().length < 6) {
                signUpFragmentBinding.passwordTextInputLayout.error = "Minimum password length is 6"
            } else {
                signUpFragmentBinding.passwordTextInputLayout.error = null
                if (!TextUtils.isEmpty(signUpFragmentBinding.confirmPasswordTextView.text.toString()) && signUpFragmentBinding.confirmPasswordTextView.text.toString() != signUpFragmentBinding.passwordTextView.text.toString()) {
                    signUpFragmentBinding.confirmPasswordTextInputLayout.error =
                        "Passwords don't match"
                } else {
                    signUpFragmentBinding.confirmPasswordTextInputLayout.error = null
                }
            }
        }
        signUpFragmentBinding.confirmPasswordTextView.addTextChangedListener {

            if (signUpFragmentBinding.passwordTextView.text.toString() != signUpFragmentBinding.confirmPasswordTextView.text.toString()) {
                signUpFragmentBinding.confirmPasswordTextInputLayout.error = "Passwords don't match"
            } else {
                signUpFragmentBinding.confirmPasswordTextInputLayout.error = null
            }
        }

        signUpFragmentBinding.signUpButtonView.setOnClickListener {
            val email: String = signUpFragmentBinding.emailTextView.text.toString().trim()
            val password: String = signUpFragmentBinding.passwordTextView.text.toString()
            val confirmPassword: String =
                signUpFragmentBinding.confirmPasswordTextView.text.toString()
            if (checkForEmptyFields(email, password, confirmPassword)) {
                Log.d("pass0", "$password $confirmPassword")

                if (password == confirmPassword) {
                    if (NetworkChecks.isNetworkConnected(activity)) {
                        signUpFragmentBinding.progressBarView.visibility = View.VISIBLE
                        auth.userSignUp(email, password)
                    } else {
                        showAlert(
                            getString(R.string.no_internet),
                            getString(R.string.no_internet_message)
                        )
                    }

                    auth.onSuccess = {
                        Toast.makeText(activity, "new user", Toast.LENGTH_SHORT).show()
                        signUpFragmentBinding.progressBarView.visibility = View.INVISIBLE
                        changeToHomeScreen()
                    }

                    auth.onFailure = {
                        signUpFragmentBinding.progressBarView.visibility = View.INVISIBLE
                        Log.d("exp", it.toString())
                        showAlert(getString(R.string.error), it)
                    }
                } else {
                    signUpFragmentBinding.confirmPasswordTextInputLayout.error =
                        "Passwords don't match"
                    Toast.makeText(activity, "bad user", Toast.LENGTH_SHORT).show()
                    Log.d("bad", "user")
                }
            }
        }
    }

    /**
     * Shows alert with [message] when exception occur on firebase auth.
     */
    private fun showAlert(title: String, message: String?) {

        context?.let {
            AlertDialog.Builder(it).setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
    }

    /**
     * Checks whether fields are empty or not using [email], [password] and [confirmPassword] values.
     * @return Boolean value indicating empty or non-empty.
     */
    private fun checkForEmptyFields(
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {

        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(
                confirmPassword
            )
        ) {
            signUpFragmentBinding.emailTextInputLayout.error = getString(R.string.email_error)
            signUpFragmentBinding.passwordTextInputLayout.error = getString(R.string.password_error)
            signUpFragmentBinding.confirmPasswordTextInputLayout.error =
                getString(R.string.password_error)
            return false
        } else if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            signUpFragmentBinding.emailTextInputLayout.error = getString(R.string.email_error)
            return false
        } else if (TextUtils.isEmpty(password)) {

            signUpFragmentBinding.passwordTextInputLayout.error = getString(R.string.password_error)
            return false

        }

        return true
    }

    /**
     * Switch from login activity to home screen activity
     */
    private fun changeToHomeScreen() {
        val activityIntent = Intent(activity, HomeScreenActivity::class.java)
        startActivity(activityIntent)
        activity?.finish()
    }


}