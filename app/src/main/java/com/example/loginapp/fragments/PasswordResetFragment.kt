package com.example.loginapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.loginapp.databinding.FragmentPasswordResetBinding

class PasswordResetFragment : Fragment() {

    private lateinit var passwordResetFragmentBinding: FragmentPasswordResetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        passwordResetFragmentBinding = FragmentPasswordResetBinding.inflate(inflater, container, false)
        return  passwordResetFragmentBinding.root
    }
}