package com.example.loginapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginapp.model.NewsData
import com.example.loginapp.model.NewsContent
import com.example.loginapp.R
import com.example.loginapp.databinding.NewsRecylcerLayoutBinding
import com.example.loginapp.db.FavouritesEntity


class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var favNews: MutableList<NewsContent> = arrayListOf()
    private var favouriteNews = ArrayList<FavouritesEntity>()
    private var newsData: NewsData? = null
    var onFavButtonChecked: ((NewsContent)->Unit)? = null
    var onFavButtonUnChecked: ((NewsContent)->Unit)? = null

    fun getNewsData(news: NewsData?) {
        newsData = news
    }

    var onCardClick: ((NewsContent) -> Unit)? = null

    lateinit var context: Context

    class NewsViewHolder(val binding: NewsRecylcerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            newsData: NewsContent?,
            category: String,
            favourites: List<NewsContent>
        ) {
            binding.category.text = category
            binding.newsTitle.text = newsData?.title
            binding.dateView.text = newsData?.date
            Glide.with(binding.root.context).load(newsData?.imageUrl)
                .error(R.drawable.news)
                .into(binding.newsPicture)
            binding.favCheckBox.isChecked = favourites.contains(newsData)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val viewBinding =
            NewsRecylcerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return NewsViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(
            newsData?.data?.let { it[position] },
            newsData?.category ?: "N/A",
            favNews
        )
        holder.binding.favCheckBox.setOnClickListener {

            val news = newsData?.data?.get(position)
            if (holder.binding.favCheckBox.isChecked) {
                news?.let { onFavButtonChecked?.invoke(it) }
            } else {
                news?.let { onFavButtonUnChecked?.invoke(it) }

            }
        }
        holder.binding.newsCard.setOnClickListener {

            newsData?.data?.get(position)?.let { onCardClick?.invoke(it) }

        }

    }

    override fun getItemCount(): Int {
        return newsData?.data?.size ?: 0
    }

    fun updateFavoriteList(news: List<FavouritesEntity>) {
        favouriteNews.clear()
        favouriteNews.addAll(news)
        notifyDataSetChanged()
        createNewsArray()
    }

    private fun createNewsArray() {
        favNews.clear()
        for (entity in favouriteNews) {
            favNews.add(entity.favouriteNews)
        }
    }
}


