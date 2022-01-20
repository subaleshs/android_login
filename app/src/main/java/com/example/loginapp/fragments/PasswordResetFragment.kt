package com.example.loginapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.loginapp.utils.NetworkChecks
import com.example.loginapp.R
import com.example.loginapp.databinding.FragmentPasswordResetBinding
import com.example.loginapp.viewmodel.AuthViewModel

class PasswordResetFragment : Fragment() {

    private lateinit var passwordResetFragmentBinding: FragmentPasswordResetBinding
    lateinit var viewModel: AuthViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        passwordResetFragmentBinding =
            FragmentPasswordResetBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        viewModelObserve()
        return passwordResetFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passwordResetFragmentBinding.progressBarView.visibility = View.INVISIBLE
        passwordResetFragmentBinding.emailTextView.addTextChangedListener {
            passwordResetFragmentBinding.emailTextInputLayout.error = null
        }

        passwordResetFragmentBinding.sendEmailButtonView.setOnClickListener {
            val userEmail = passwordResetFragmentBinding.emailTextView.text.toString().trim()
            if (TextUtils.isEmpty(userEmail) or !Patterns.EMAIL_ADDRESS.matcher(userEmail)
                    .matches()
            ) {
                passwordResetFragmentBinding.emailTextInputLayout.error =
                    getString(R.string.email_error)
            } else {
                if (NetworkChecks.isNetworkConnected(activity)) {
                    passwordResetFragmentBinding.progressBarView.visibility = View.VISIBLE
                    viewModel.resetPassword(userEmail)

                } else {
                    context?.let {
                        AlertDialog.Builder(it)
                            .setTitle(R.string.no_internet)
                            .setMessage(R.string.no_internet_message)
                            .setPositiveButton(android.R.string.ok) { _, _ -> }
                            .setIcon(android.R.drawable.ic_dialog_alert).show()
                    }
                }
            }
        }
    }

    private fun viewModelObserve() {
        val builder = AlertDialog.Builder(context)
        viewModel.getExceptionMessage().observe(this, { message ->
            if (message == null) {
                builder.setMessage(R.string.email_send_success)
                builder.setPositiveButton(R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                    passwordResetFragmentBinding.progressBarView.visibility =
                        View.INVISIBLE
                    parentFragmentManager.popBackStack()
                }
                builder.show()
            } else {
                passwordResetFragmentBinding.progressBarView.visibility =
                    View.INVISIBLE
                context?.let {
                    AlertDialog.Builder(it)
                        .setTitle(R.string.error)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok) { _, _ -> }
                        .setIcon(android.R.drawable.ic_dialog_alert).show()
                }
            }
        })
    }

}