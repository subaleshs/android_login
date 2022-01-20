package com.example.loginapp.fragments

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.example.loginapp.R
import com.example.loginapp.databinding.FragmentDetailedNewsBinding
import com.example.loginapp.model.NewsContent

class DetailedNewsFragment : Fragment() {

    private lateinit var binding: FragmentDetailedNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailedNewsBinding.inflate(inflater, container, false)
        val newsDetail = arguments?.getParcelable<NewsContent>("full_news")
        binding.newsTitleText.text = newsDetail?.title ?: "title"
        binding.newsContent.text = newsDetail?.content ?: "Content"
        context?.let { Glide.with(it).load(newsDetail?.imageUrl).error(R.drawable.news).into(binding.newsImage) }

        if (newsDetail?.readMoreUrl != null){
            val htmlATag = "<a href=\""+newsDetail?.readMoreUrl+"\">Read More</a>"
            println(htmlATag)
            binding.readMoreLink.text = HtmlCompat.fromHtml(htmlATag,HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.readMoreLink.movementMethod = LinkMovementMethod.getInstance()
        }
        return binding.root
    }

}