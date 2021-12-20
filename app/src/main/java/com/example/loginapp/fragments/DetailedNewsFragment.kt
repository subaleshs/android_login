package com.example.loginapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
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
        context?.let { Glide.with(it).load(newsDetail?.get(4)).error(R.drawable.news).into(binding.newsImage) }

        val htmlATag = "<a href=\""+newsDetail?.get(2)+"\">Read More</a>"
        println(htmlATag)
        binding.readMoreLink.text = HtmlCompat.fromHtml(htmlATag,HtmlCompat.FROM_HTML_MODE_LEGACY)
        return binding.root
    }

}