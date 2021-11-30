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


class NewsFeedFragment() : Fragment() {


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

        val jsonObject = JSONObject(getJSON("news.json"))

        val newsDataList = getNews(jsonObject)

        newsFragmentBinding.newsRecyclerLayout.adapter = NewsAdapter(newsDataList)


    }

    private fun getNews(jsonObject: JSONObject): ArrayList<NewsTitle> {

        val newsArrayList: ArrayList<NewsTitle> = ArrayList()
        val jsonArray = jsonObject.getJSONArray("data")

        for(index in 0 until jsonArray.length()){
            val news = jsonArray.getJSONObject(index)

            newsArrayList.add(
                NewsTitle(
                    news.getString("title"),
                    news.getString("author"),
                    news.getString("date")
                )
            )
        }

        return  newsArrayList
    }

    private fun getJSON(fileName: String): String {

        var json = "{}"

        try {
            val jsonFile = context?.assets?.open(fileName)
            json =  jsonFile?.bufferedReader().use { it!!.readText() }
        }
        catch (exception: IOException){
            exception.printStackTrace()
            return "{}"
        }

        return json

    }


}