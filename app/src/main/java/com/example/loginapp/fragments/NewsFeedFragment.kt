package com.example.loginapp.fragments

import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.loginapp.model.NewsContent
import com.example.loginapp.R
import com.example.loginapp.adapters.NewsAdapter
import com.example.loginapp.databinding.FragmentNewsFeedBinding
import com.example.loginapp.viewmodel.NewsViewModel


class NewsFeedFragment : Fragment() {

    private var newsAdapter = NewsAdapter()
//    var onNewsExpand: ((NewsContent) -> Unit)? = null
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
        newsFragmentBinding.swipeRefresh.setOnRefreshListener {
            viewModelInit()
            newsFragmentBinding.newsRecyclerLayout.adapter = newsAdapter
            newsFragmentBinding.swipeRefresh.isRefreshing = false
        }

        if (checkNetwork()){
            newsFragmentBinding.noNetworkImage.visibility = View.INVISIBLE
            newsFragmentBinding.noInternet.visibility = View.INVISIBLE

            viewModelInit()
            newsFragmentBinding.newsRecyclerLayout.adapter = newsAdapter
            newsAdapter.onCardClick = { detailedNews: NewsContent ->
                val transaction = parentFragmentManager.beginTransaction()
                val fragment = DetailedNewsFragment()
                val newsBundle = Bundle()

                newsBundle.putParcelable("full_news", detailedNews)
                fragment.arguments = newsBundle
                transaction.apply {
                    replace(R.id.fragmentContainerView, fragment)
                    addToBackStack("home")
                    commit()
                }
            }
        }
        else{
            newsFragmentBinding.noNetworkImage.visibility = View.VISIBLE
            newsFragmentBinding.noInternet.visibility = View.VISIBLE
        }
    }

    private fun checkNetwork(): Boolean {
        val connectivityManager = activity?.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            return connectivityManager.activeNetwork != null
        }
        else{
            @Suppress("DEPRECATION")
            return connectivityManager.activeNetworkInfo != null
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = "Home"
    }

    private fun viewModelInit() {
        val viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        viewModel.getLiveData().observe(this, {
            if (it != null) {
                newsAdapter.getNewsData(it)
                newsAdapter.notifyDataSetChanged()
                println("done")
            } else {
                if (checkNetwork()){
                    newsFragmentBinding.noNetworkImage.visibility = View.VISIBLE
                    newsFragmentBinding.noInternet.visibility = View.VISIBLE
                }
                else{
                    newsFragmentBinding.noNetworkImage.visibility = View.VISIBLE
                    newsFragmentBinding.noInternet.visibility = View.VISIBLE
                }
            }
        })

        viewModel.getNewsfromAPI("all")
    }
}