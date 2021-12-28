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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.loginapp.activities.HomeScreenActivity
import com.example.loginapp.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth
import java.lang.NullPointerException

class SignUpFragment : Fragment() {

    private lateinit var signUpFragmentBinding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        signUpFragmentBinding = FragmentSignupBinding.inflate(inflater, container, false)
        return signUpFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            val email: String = signUpFragmentBinding.emailTextView.text.toString()
            val password: String = signUpFragmentBinding.passwordTextView.text.toString()
            val confirmPassword: String =
                signUpFragmentBinding.confirmPasswordTextView.text.toString()
            if (checkForEmptyFields(email, password, confirmPassword)) {
                Log.d("pass0", "$password $confirmPassword")

                if (password == confirmPassword) {
                    registerUser(email, password)
                } else {
                    signUpFragmentBinding.confirmPasswordTextInputLayout.error =
                        "Passwords don't match"
                    Toast.makeText(activity, "bad user", Toast.LENGTH_SHORT).show()
                    Log.d("bad", "user")
                }
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(activity, "new user", Toast.LENGTH_SHORT).show()
                    changeToHomeScreen(
                        FirebaseAuth.getInstance().currentUser?.email,
                        FirebaseAuth.getInstance().currentUser?.uid
                    )

                } else {
                    Log.d("exp", it.exception.toString())
                    showAlert(it.exception?.message)
                }
            }
    }

    private fun showAlert(message: String?) {
        try {
            val builder = AlertDialog.Builder(activity!!)
            builder.setMessage(message)
            builder.setPositiveButton("cancel"){
                dialog, _ -> dialog.dismiss()
            }
            builder.show()
        }catch (e: NullPointerException){
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()

        }
    }

    private fun checkForEmptyFields(
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        /*
        Function checks whether fields are empty or not
         */
        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(
                confirmPassword
            )
        ) {
            signUpFragmentBinding.emailTextInputLayout.error = "Valid Email Required."
            signUpFragmentBinding.passwordTextInputLayout.error = "Valid Password Required."
            signUpFragmentBinding.confirmPasswordTextInputLayout.error = "Valid Password Required."
            return false
        } else if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            signUpFragmentBinding.emailTextInputLayout.error = "Valid Email Required."
            return false
        } else if (TextUtils.isEmpty(password)) {

            signUpFragmentBinding.passwordTextInputLayout.error = "Valid Password Required"
            return false

        }

        return true
    }

    private fun changeToHomeScreen(emailAddress: String?, uid: String?) {
        /*
        Method to switch to home screen
         */

        val loginSharedPreferences = activity?.getSharedPreferences(
            "user",
            AppCompatActivity.MODE_PRIVATE
        )
        val loginEditor = loginSharedPreferences?.edit()
        loginEditor?.putString("email", emailAddress)
        loginEditor?.putString("uid", uid)
        loginEditor?.apply()

        val activityIntent = Intent(activity, HomeScreenActivity::class.java)
        startActivity(activityIntent)
        activity?.finish()
    }


}