package com.example.loginapp.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.loginapp.R
import com.example.loginapp.adapters.FavouritesAdapter
import com.example.loginapp.databinding.FragmentFavouritesBinding
import com.example.loginapp.model.Articles
import com.example.loginapp.model.NewsContent
import com.example.loginapp.utils.EditPreference
import com.google.firebase.auth.FirebaseAuth

class FavouritesFragment : Fragment() {

    private lateinit var favouriteFragmentBinding: FragmentFavouritesBinding
    private lateinit var favourites: MutableList<Articles>
    lateinit var editPreferences: EditPreference
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
        val favPreference = context?.getSharedPreferences(
            FirebaseAuth.getInstance().currentUser?.uid.toString(),
            MODE_PRIVATE
        )
        if (favPreference != null) {
            if (favPreference.contains("Favourite")) {
                editPreferences = EditPreference(favPreference)
                favourites = editPreferences.getPreference()
                Log.d("list", favourites.toString())
                if (favourites.size == 0) {
                    favouriteFragmentBinding.noFavoritesText.visibility = View.VISIBLE
                    favouriteFragmentBinding.newsRecyclerLayout.adapter = favAdapter
                } else {
                    favouriteFragmentBinding.noFavoritesText.visibility = View.INVISIBLE
                    favAdapter.getFavNews(favourites)
                    favouriteFragmentBinding.newsRecyclerLayout.adapter = favAdapter
                }
            } else {
                favouriteFragmentBinding.noFavoritesText.visibility = View.VISIBLE
            }

        }

        favAdapter.emptyFavourites = {
            favouriteFragmentBinding.noFavoritesText.visibility = View.VISIBLE
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
        val sharedPreferences = context?.getSharedPreferences(
            FirebaseAuth.getInstance().currentUser?.uid.toString(),
            MODE_PRIVATE
        )
        if (sharedPreferences?.contains("Favourite") == true) {
            val fav = EditPreference(sharedPreferences).getPreference()
            favAdapter.getFavNews(fav)
            favouriteFragmentBinding.newsRecyclerLayout.adapter = favAdapter

            if (fav.size == 0) {
                favouriteFragmentBinding.noFavoritesText.visibility = View.VISIBLE
                favouriteFragmentBinding.newsRecyclerLayout.visibility = View.INVISIBLE
            } else {
                favouriteFragmentBinding.noFavoritesText.visibility = View.INVISIBLE
                favouriteFragmentBinding.newsRecyclerLayout.visibility = View.VISIBLE
            }
        }
    }


}