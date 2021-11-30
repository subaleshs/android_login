package com.example.loginapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.loginapp.fragments.AccountFragment
import com.example.loginapp.fragments.FavoritesFragment
import com.example.loginapp.fragments.NewsFeedFragment

private const val numberOfTabs = 3

class SwipeViewAdapter(
    private val fragmentManager: FragmentManager, private val lifecycle: Lifecycle, private val newsFeedFragment: NewsFeedFragment, private val accountFragment: AccountFragment
): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return numberOfTabs
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->newsFeedFragment
            1->FavoritesFragment()
            else->accountFragment
        }

    }
}