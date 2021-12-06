package com.example.loginapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.loginapp.NewsContent
import com.example.loginapp.R
import com.example.loginapp.databinding.FragmentDetailedNewsBinding

class DetailedNewsFragment : Fragment() {

    private lateinit var binding: FragmentDetailedNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailedNewsBinding.inflate(inflater, container, false)
        val newsDetail = arguments?.getStringArrayList("news")
        binding.newsTitleText.text = newsDetail?.get(0) ?: "title"
        binding.newsContent.text = newsDetail?.get(1) ?: "Content"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}