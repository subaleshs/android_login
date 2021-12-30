package com.example.loginapp.fragments

import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.loginapp.NetworkChecks
import com.example.loginapp.model.NewsContent
import com.example.loginapp.R
import com.example.loginapp.adapters.NewsAdapter
import com.example.loginapp.databinding.FragmentNewsFeedBinding
import com.example.loginapp.viewmodel.NewsViewModel


class NewsFeedFragment : Fragment() {

    private var newsAdapter = NewsAdapter()

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

        if (NetworkChecks().isNetworkConnected(activity)) {
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
        } else {
            newsFragmentBinding.noNetworkImage.visibility = View.VISIBLE
            newsFragmentBinding.noInternet.visibility = View.VISIBLE
            newsFragmentBinding.newsRecyclerLayout.visibility = View.INVISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.home_title)
    }

    private fun viewModelInit() {
        val viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        viewModel.getLiveData().observe(this, {
            if (it != null) {
                newsAdapter.getNewsData(it)
                newsAdapter.notifyDataSetChanged()
            } else {
                if (NetworkChecks().isNetworkConnected(activity)) {
                    newsFragmentBinding.noNetworkImage.setImageResource(R.drawable.tiny_people_examining_operating_system_error_warning_web_page_isolated_flat_illustration_74855_11104)
                    newsFragmentBinding.noInternet.setText(R.string.refresh_message)
                    if (newsFragmentBinding.noNetworkImage.visibility == View.VISIBLE) {
                        newsFragmentBinding.noNetworkImage.visibility = View.INVISIBLE
                        newsFragmentBinding.noInternet.visibility = View.INVISIBLE
                        newsFragmentBinding.newsRecyclerLayout.visibility = View.VISIBLE
                    } else {
                        newsFragmentBinding.noNetworkImage.visibility = View.VISIBLE
                        newsFragmentBinding.noInternet.visibility = View.VISIBLE
                        newsFragmentBinding.newsRecyclerLayout.visibility = View.INVISIBLE
                    }

                    Log.d("it", it.toString())

                } else {
                    newsFragmentBinding.noNetworkImage.setImageResource(R.drawable.wireless)
                    newsFragmentBinding.noInternet.setText(R.string.network_error)
                    newsFragmentBinding.noNetworkImage.visibility = View.VISIBLE
                    newsFragmentBinding.noInternet.visibility = View.VISIBLE
                    newsFragmentBinding.newsRecyclerLayout.visibility = View.INVISIBLE
                    Log.d("count", "wer")
                }
            }
        })

        viewModel.getNewsfromAPI("all")
    }
}