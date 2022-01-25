package com.example.loginapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginapp.R
import com.example.loginapp.databinding.NewsRecylcerLayoutBinding
import com.example.loginapp.db.FavouritesEntity
import com.example.loginapp.model.NewsContent

class FavouritesAdapter: RecyclerView.Adapter<FavouritesAdapter.FavoritesViewHolder>() {

    private var favorites = ArrayList<FavouritesEntity>()
    var onFavButtonClick: ((FavouritesEntity)->Unit)? = null
    var expandNews: ((NewsContent)->Unit)? = null

//    fun getFav(favouritesArray: MutableList<NewsContent>) {
//        this.favouritesArray = favouritesArray
//    }

    fun getFavNews(news: List<FavouritesEntity>) {
        favorites.clear()
        favorites.addAll(news)
        notifyDataSetChanged()
    }

    class FavoritesViewHolder(val binding: NewsRecylcerLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(news: NewsContent?, category: String = "all"){
            binding.newsTitle.text = news?.title
            binding.category.text = category
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
        holder.bind(favorites[position].favouriteNews)
        holder.binding.favCheckBox.setOnClickListener {
            onFavButtonClick?.invoke(favorites[position])
        }

        holder.binding.newsCard.setOnClickListener {
            expandNews?.invoke(favorites[position].favouriteNews)
        }
    }

    override fun getItemCount(): Int {
        return favorites.size
    }
}