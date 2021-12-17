package com.example.loginapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.loginapp.model.NewsData
import com.example.loginapp.model.NewsContent
import com.example.loginapp.R
import com.example.loginapp.adapters.NewsAdapter
import com.example.loginapp.api.NewsApiInterface
import com.example.loginapp.databinding.FragmentNewsFeedBinding
import com.example.loginapp.viewmodel.NewsViewModel
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import retrofit2.Callback as Callback


class NewsFeedFragment : Fragment() {

    var newsAdapter = NewsAdapter()
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
        viewModelInit()
        newsFragmentBinding.newsRecyclerLayout.adapter = newsAdapter
        newsAdapter.onCardClick = { detailedNews: NewsContent ->
            val transaction = parentFragmentManager.beginTransaction()
            val fragment = DetailedNewsFragment()
            val newsBundle = Bundle()
            val newsArray = ArrayList<String?>()
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

    private fun viewModelInit() {
        val viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        viewModel.getLiveData().observe(this, {
            if (it != null) {
                newsAdapter.getNewsData(it)
                newsAdapter.notifyDataSetChanged()
                println("done")
            } else {
                Toast.makeText(context, "asdfasd", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.getNewsfromAPI("all")
    }
}