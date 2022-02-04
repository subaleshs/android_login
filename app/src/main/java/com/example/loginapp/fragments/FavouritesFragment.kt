package com.example.loginapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.loginapp.R
import com.example.loginapp.adapters.FavouritesAdapter
import com.example.loginapp.databinding.FragmentFavouritesBinding
import com.example.loginapp.viewmodel.FavoritesViewModel

class FavouritesFragment : Fragment() {

    private lateinit var favouriteFragmentBinding: FragmentFavouritesBinding
    private val favAdapter = FavouritesAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favouriteFragmentBinding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return favouriteFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favouriteFragmentBinding.noFavoritesText.visibility = View.INVISIBLE
        favouriteFragmentBinding.newsRecyclerLayout.adapter = favAdapter
        val favoritesViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        favoritesViewModel.getFavoritesLiveData().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                favouriteFragmentBinding.noFavoritesText.visibility = View.VISIBLE
                favouriteFragmentBinding.newsRecyclerLayout.visibility = View.INVISIBLE
            } else {
                favAdapter.getFavNews(it)
                favouriteFragmentBinding.noFavoritesText.visibility = View.INVISIBLE
                favouriteFragmentBinding.newsRecyclerLayout.visibility = View.VISIBLE
            }
        }

        favAdapter.onFavButtonClick = {
            favoritesViewModel.deleteFromFavorites(it)
            favAdapter.notifyDataSetChanged()
        }

        favAdapter.expandNews = {
            val transaction = parentFragmentManager.beginTransaction()
            val fragment = DetailedNewsFragment()
            val newsBundle = Bundle()
            newsBundle.putParcelable("full_news", it)
            fragment.arguments = newsBundle
            transaction.apply {
                replace(R.id.fragmentContainerView, fragment)
                addToBackStack("home")
                commit()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setTitle(R.string.fav_title)

    }


}