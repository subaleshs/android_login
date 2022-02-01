package com.example.loginapp.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.loginapp.R
import com.example.loginapp.adapters.CustomNewsAdapter
import com.example.loginapp.databinding.ActivityHomeScreenBinding
import com.example.loginapp.databinding.CustomNewsFragmentBinding
import com.example.loginapp.viewmodel.CustomNewsViewModel

class CustomNewsFragment : Fragment() {

    private lateinit var viewModel: CustomNewsViewModel
    lateinit var binding: CustomNewsFragmentBinding
    private val adapter: CustomNewsAdapter = CustomNewsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CustomNewsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.noInternet.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.INVISIBLE
        viewModel = ViewModelProvider(this).get(CustomNewsViewModel::class.java)

        binding.addNewFAB.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            val addNewsFragment = AddNewsFragment()
            fragmentTransaction.apply {
                replace(R.id.fragmentContainerView, addNewsFragment)
                addToBackStack("forgotPasswordFragment")
                setReorderingAllowed(true)
                commit()
            }
        }

//        binding.customNewsRecyclerLayout.adapter = adapter

    }
    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.your_news)
    }

}