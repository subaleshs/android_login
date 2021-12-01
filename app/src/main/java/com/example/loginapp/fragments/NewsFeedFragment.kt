package com.example.loginapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.loginapp.NewsData
import com.example.loginapp.adapters.NewsAdapter
import com.example.loginapp.databinding.FragmentNewsFeedBinding
import com.google.gson.Gson
import java.io.IOException


class NewsFeedFragment : Fragment() {


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

        val newsTitleArray = news.data
        println(newsTitleArray)
        newsFragmentBinding.newsRecyclerLayout.adapter = NewsAdapter(newsTitleArray)


    }

    private fun getNewsData(json: String): NewsData{

            return Gson().fromJson(json, NewsData::class.java)
    }


    private fun getJSONString(): String {

        var json = "{}"

        try {
            val jsonFile = context?.assets?.open("news.json")
            json =  jsonFile?.bufferedReader().use { it!!.readText() }
        }
        catch (exception: IOException){
            exception.printStackTrace()
            return json
        }

        return json

    }


}