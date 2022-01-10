package com.example.loginapp.adapters

import android.content.Context.MODE_PRIVATE
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginapp.R
import com.example.loginapp.databinding.NewsRecylcerLayoutBinding
import com.example.loginapp.model.NewsContent
import com.example.loginapp.utils.SavePreference
import com.google.firebase.auth.FirebaseAuth

class FavouritesAdapter: RecyclerView.Adapter<FavouritesAdapter.FavoritesViewHolder>() {

    private var favoritesArray: MutableList<NewsContent> = arrayListOf()

    fun getFavNews(favArray: MutableList<NewsContent>) {
        favoritesArray = favArray
    }

    class FavoritesViewHolder(val binding: NewsRecylcerLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(news: NewsContent?, category: String = "all"){
            binding.newsTitle.text = news?.title
            binding.category.text = ""
            binding.dateView.text = news?.date
            Glide.with(binding.root.context).load(news?.imageUrl)
                .error(R.drawable.news)
                .into(binding.newsPicture)
            binding.favCheckBox.isChecked = true
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesViewHolder {
        val viewBinding = NewsRecylcerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  FavoritesViewHolder(viewBinding)
    }

    override fun onBindViewHolder(
        holder: FavoritesViewHolder,
        position: Int
    ) {
        holder.bind(favoritesArray[position])

        holder.binding.favCheckBox.setOnClickListener {
            favoritesArray.removeAt(position)
            notifyDataSetChanged()
            val preference = holder.binding.root.context.getSharedPreferences(FirebaseAuth.getInstance().currentUser?.uid.toString(), MODE_PRIVATE)
            val editPreference = SavePreference(preference)
            editPreference.addPreference(favoritesArray)
        }
    }

    override fun getItemCount(): Int {
        return favoritesArray.size
    }
}