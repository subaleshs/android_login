package com.example.loginapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.loginapp.databinding.FragmentSignupBinding

class SignUpFragment : Fragment() {

    private lateinit var signUpFragmentBinding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        signUpFragmentBinding = FragmentSignupBinding.inflate(inflater, container, false)
        return signUpFragmentBinding.root
    }
}