package com.example.loginapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.loginapp.NewsData
import com.example.loginapp.NewsContent
import com.example.loginapp.R
import com.example.loginapp.adapters.NewsAdapter
import com.example.loginapp.databinding.FragmentNewsFeedBinding
import com.google.gson.Gson
import java.io.IOException


class NewsFeedFragment : Fragment() {


    var onNewsExpand: ((NewsContent)-> Unit)? = null
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


//        Log.d("TYPE", news::class.simpleName.toString())
        println(news)

        val newsTitleArray = news.data
        println(newsTitleArray)
        val newsAdapter = NewsAdapter(news)

        newsFragmentBinding.newsRecyclerLayout.adapter = newsAdapter

        newsAdapter.onCardClick = {newsFed: NewsContent ->

            println(newsFed)
//            val viewPager2 = activity!!.findViewById<ViewPager2>(R.id.viewPagerBottomNav)
//            viewPager2.currentItem = 3
//            onNewsExpand?.invoke(newsFed)

//            val transaction = parentFragmentManager.beginTransaction()
//            println(parentFragment)
//
//            transaction.apply {
//                replace(R.id.fragmentContainerView, DetailedNewsFragment())
//                addToBackStack(null)
//                commit()
//            }

        }
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