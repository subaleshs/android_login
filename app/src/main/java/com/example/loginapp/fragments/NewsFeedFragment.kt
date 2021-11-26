package com.example.loginapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.loginapp.NewsAdapter
import com.example.loginapp.NewsTitle
import com.example.loginapp.databinding.FragmentNewsFeedBinding
import org.json.JSONObject
import java.io.IOException


class NewsFeedFragment(private val newsTitle: ArrayList<NewsTitle>) : Fragment() {

    private val newsTitleArray = newsTitle

    private lateinit var newsFragmentBinding: FragmentNewsFeedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        newsFragmentBinding = FragmentNewsFeedBinding.inflate(inflater, container, false)

        return newsFragmentBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsFragmentBinding.newsRecyclerLayout.adapter = NewsAdapter(newsTitleArray)


    }

}