package com.example.loginapp.adapters

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginapp.model.NewsData
import com.example.loginapp.model.NewsContent
import com.example.loginapp.R
import com.example.loginapp.databinding.NewsRecylcerLayoutBinding
import com.example.loginapp.db.FavouritesEntity
import com.example.loginapp.utils.EditPreference
import com.google.firebase.auth.FirebaseAuth

class
NewsAdapter :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    var favouritesNews: MutableList<NewsContent> = arrayListOf()
    private var newsData: NewsData? = null
    lateinit var preference: SharedPreferences
    lateinit var editPreference: EditPreference

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
            favourites: MutableList<NewsContent>
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
        preference = parent.context.getSharedPreferences(
            FirebaseAuth.getInstance().currentUser?.uid.toString(),
            MODE_PRIVATE
        )
        editPreference = EditPreference(preference)
        if (editPreference.checkPreferenceExist()) {
            favouritesNews = editPreference.getPreference()
        }
        return NewsViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(
            newsData?.data?.let { it[position] },
            newsData?.category ?: "N/A",
            favouritesNews
        )
        if (editPreference.checkPreferenceExist()) {
            favouritesNews = editPreference.getPreference()
        }
        holder.binding.favCheckBox.setOnClickListener {

            val news = newsData?.data?.get(position)
            if (holder.binding.favCheckBox.isChecked) {
                news?.let { favouritesNews.add(it) }
                editPreference.addPreference(favouritesNews)
                val fav = news?.let { it1 -> FavouritesEntity(it1) }
            } else {
                favouritesNews.removeAt(favouritesNews.indexOf(news))
                editPreference.addPreference(favouritesNews)
            }
        }
        holder.binding.newsCard.setOnClickListener {

            newsData?.data?.get(position)?.let { onCardClick?.invoke(it) }

        }

    }

    override fun getItemCount(): Int {
        return newsData?.data?.size ?: 0
    }
}


