package com.example.loginapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.loginapp.fragments.AccountFragment
import com.example.loginapp.fragments.DetailedNewsFragment
import com.example.loginapp.fragments.FavoritesFragment
import com.example.loginapp.fragments.NewsFeedFragment

private const val numberOfTabs =3

class SwipeViewAdapter(
    private val fragmentManager: FragmentManager, private val lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return numberOfTabs
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->NewsFeedFragment()
            1->FavoritesFragment()
            else->AccountFragment()
        }

    }
}