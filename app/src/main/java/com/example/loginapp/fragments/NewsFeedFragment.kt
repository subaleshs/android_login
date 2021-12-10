package com.example.loginapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.loginapp.model.NewsData
import com.example.loginapp.model.NewsContent
import com.example.loginapp.R
import com.example.loginapp.adapters.NewsAdapter
import com.example.loginapp.api.NewsApi
import com.example.loginapp.databinding.FragmentNewsFeedBinding
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import retrofit2.Callback as Callback


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

        getNewsFromAPI("science")

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

    private fun getNewsFromAPI(category: String){

        val api = Retrofit.Builder().baseUrl("https://inshortsapi.vercel.app/").addConverterFactory(GsonConverterFactory.create()).client(getClient()).build().create(NewsApi::class.java)

        val apiCall = api.getNews(category)

        val result = apiCall.enqueue(object: Callback<NewsData>{
            override fun onFailure(call: Call<NewsData>, t: Throwable) {
                Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {
                val news = response.body()
                print(7)
            }
        })
    }

    private fun getClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        return builder.build()
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