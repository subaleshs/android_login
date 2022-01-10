package com.example.loginapp.fragments

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.loginapp.utils.NetworkChecks
import com.example.loginapp.model.NewsContent
import com.example.loginapp.R
import com.example.loginapp.adapters.NewsAdapter
import com.example.loginapp.databinding.FragmentNewsFeedBinding
import com.example.loginapp.utils.SavePreference
import com.example.loginapp.viewmodel.NewsViewModel
import com.google.firebase.auth.FirebaseAuth


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
        val viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        if (NetworkChecks.isNetworkConnected(activity)) {
            hideErrorImage()
            newsFragmentBinding.progressBarView.visibility = View.VISIBLE
            viewModelInit(viewModel)
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
            showNoInternetImage()
        }

        newsFragmentBinding.swipeRefresh.setOnRefreshListener {
            newsFragmentBinding.progressBarView.visibility = View.VISIBLE
            if (newsFragmentBinding.noNetworkImage.visibility == View.VISIBLE && NetworkChecks.isNetworkConnected(
                    activity
                )
            ) {
                hideErrorImage()
            }
            viewModelInit(viewModel)
            newsFragmentBinding.newsRecyclerLayout.adapter = newsAdapter
            newsFragmentBinding.swipeRefresh.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.home_title)
        newsAdapter.notifyDataSetChanged()
    }

    private fun viewModelInit(viewModel: NewsViewModel) {
        if (NetworkChecks.isNetworkConnected(activity)) {
            viewModel.getLiveData().observe(this, {
                println("$it  vp")
                if (it != null && it.success) {
                    hideErrorImage()
                    newsFragmentBinding.progressBarView.visibility = View.INVISIBLE
                    newsFragmentBinding.newsRecyclerLayout.visibility = View.VISIBLE
                    newsAdapter.getNewsData(it)
                    newsAdapter.notifyDataSetChanged()
                } else {
                    if (it?.success == false) {
                        context?.let { it1 ->
                            AlertDialog.Builder(it1)
                                .setTitle(R.string.error)
                                .setMessage(it.error)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show()
                        }
                    } else {

                    }
                    showRefreshImage()
                }
            })
            viewModel.getNewsFromRepo("all")
        } else {
            showNoInternetImage()
        }
    }

    private fun showRefreshImage() {
        newsFragmentBinding.progressBarView.visibility = View.INVISIBLE
        newsFragmentBinding.noNetworkImage.setImageResource(R.drawable.error_image)
        newsFragmentBinding.noInternet.text = getString(R.string.refresh)
        if (newsFragmentBinding.noNetworkImage.visibility == View.VISIBLE) {
            newsFragmentBinding.noNetworkImage.visibility = View.INVISIBLE
            newsFragmentBinding.noInternet.visibility = View.INVISIBLE
            newsFragmentBinding.newsRecyclerLayout.visibility = View.VISIBLE
        } else {
            newsFragmentBinding.noNetworkImage.visibility = View.VISIBLE
            newsFragmentBinding.noInternet.visibility = View.VISIBLE
            newsFragmentBinding.newsRecyclerLayout.visibility = View.INVISIBLE
        }
    }

    private fun showNoInternetImage() {
        newsFragmentBinding.progressBarView.visibility = View.INVISIBLE
        newsFragmentBinding.noNetworkImage.setImageResource(R.drawable.no_internet)
        newsFragmentBinding.noInternet.setText(R.string.network_error)
        newsFragmentBinding.noNetworkImage.visibility = View.VISIBLE
        newsFragmentBinding.noInternet.visibility = View.VISIBLE
        newsFragmentBinding.newsRecyclerLayout.visibility = View.INVISIBLE
    }

    private fun hideErrorImage() {
        newsFragmentBinding.noNetworkImage.visibility = View.INVISIBLE
        newsFragmentBinding.noInternet.visibility = View.INVISIBLE
    }
}

