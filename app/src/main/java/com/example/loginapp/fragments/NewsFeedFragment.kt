package com.example.loginapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.loginapp.dataclasses.NewsData
import com.example.loginapp.dataclasses.NewsContent
import com.example.loginapp.R
import com.example.loginapp.adapters.NewsAdapter
import com.example.loginapp.databinding.FragmentNewsFeedBinding
import com.google.gson.Gson
import java.io.IOException


class NewsFeedFragment : Fragment() {


    var onNewsExpand: ((NewsContent) -> Unit)? = null
    private lateinit var newsFragmentBinding: FragmentNewsFeedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        newsFragmentBinding = FragmentNewsFeedBinding.inflate(inflater, container, false)

        return newsFragmentBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val jsonString = getJSONString()
        val news = getNewsData(jsonString)
        val newsAdapter = NewsAdapter(news)

        newsFragmentBinding.newsRecyclerLayout.adapter = newsAdapter
        newsAdapter.onCardClick = { detailedNews: NewsContent ->

            val transaction = parentFragmentManager.beginTransaction()
            val fragment = DetailedNewsFragment()
            val newsBundle = Bundle()
            val newsArray = ArrayList<String>()

            newsArray.add(detailedNews.title)
            newsArray.add(detailedNews.content)
            newsArray.add(detailedNews.readMoreUrl)
            newsArray.add(detailedNews.date)
            newsArray.add(detailedNews.imageUrl)
            newsBundle.putStringArrayList("news", newsArray)
            fragment.arguments = newsBundle
            transaction.apply {
                replace(R.id.fragmentContainerView, fragment)
                addToBackStack("home")
                commit()
            }

        }
    }


    private fun getNewsData(json: String?): NewsData {
        return Gson().fromJson(json, NewsData::class.java)
    }


    private fun getJSONString(): String? {

        var json: String? = null

        try {
            val jsonFile = context?.assets?.open("news.json")
            json = jsonFile?.bufferedReader()?.use { it.readText() }
        } catch (exception: IOException) {
            exception.printStackTrace()
            return json
        }

        return json

    }


}