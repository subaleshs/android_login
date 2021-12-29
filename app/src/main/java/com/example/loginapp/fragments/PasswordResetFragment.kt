package com.example.loginapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.loginapp.NetworkChecks
import com.example.loginapp.databinding.FragmentPasswordResetBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import java.lang.NullPointerException

class PasswordResetFragment : Fragment() {

    private lateinit var passwordResetFragmentBinding: FragmentPasswordResetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        passwordResetFragmentBinding = FragmentPasswordResetBinding.inflate(inflater, container, false)
        return  passwordResetFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passwordResetFragmentBinding.progressBarView.visibility = View.INVISIBLE
        passwordResetFragmentBinding.emailTextView.addTextChangedListener {
            passwordResetFragmentBinding.emailTextInputLayout.error = null
        }

        passwordResetFragmentBinding.sendEmailButtonView.setOnClickListener {
            val userEmail = passwordResetFragmentBinding.emailTextView.text.toString().trim()
            if (TextUtils.isEmpty(userEmail) or !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                passwordResetFragmentBinding.emailTextInputLayout.error = "Valid email required."
            }
            else{
                if (NetworkChecks().isNetworkConnected(activity)){
                    passwordResetFragmentBinding.progressBarView.visibility = View.VISIBLE
                    FirebaseAuth.getInstance().sendPasswordResetEmail(userEmail)
                        .addOnCompleteListener {
                            val builder = AlertDialog.Builder(context)
                            if (it.isSuccessful){
                                builder.setMessage("Email send successfully.")
                                builder.setPositiveButton("Ok"){
                                        dialog, _ ->
                                    dialog.dismiss()
                                    passwordResetFragmentBinding.progressBarView.visibility = View.INVISIBLE
                                    parentFragmentManager.popBackStack()
                                }
                                builder.show()
                            }else{
                                passwordResetFragmentBinding.progressBarView.visibility = View.INVISIBLE
                                    val alertDialog = androidx.appcompat.app.AlertDialog.Builder(context!!).setTitle("ERROR")
                                        .setMessage(it.exception?.message)
                                        .setPositiveButton(android.R.string.ok) { _, _ -> }
                                        .setIcon(android.R.drawable.ic_dialog_alert).show()
                            }
                        }
                }else{
                    try {
                        androidx.appcompat.app.AlertDialog.Builder(context!!).setTitle("No Internet Connection")
                            .setMessage("Please check your internet connection and try again")
                            .setPositiveButton(android.R.string.ok) { _, _ -> }
                            .setIcon(android.R.drawable.ic_dialog_alert).show()
                    } catch (e: NullPointerException) {
                        Toast.makeText(
                            activity,
                            "Please check your internet connection and try again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}