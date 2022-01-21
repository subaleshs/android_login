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
import com.example.loginapp.model.Articles
import com.example.loginapp.model.News
import com.example.loginapp.utils.EditPreference
import com.google.firebase.auth.FirebaseAuth

class
NewsAdapter :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    var favouritesNews: MutableList<Articles> = arrayListOf()
    private var newsData: News? = null
    lateinit var preference: SharedPreferences
    lateinit var editPreference: EditPreference

    fun getNewsData(news: News?) {
        newsData = news
    }

    var onCardClick: ((Articles) -> Unit)? = null

    lateinit var context: Context

    class NewsViewHolder(val binding: NewsRecylcerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            newsData: Articles?,
            favourites: MutableList<Articles>
        ) {
            binding.category.text = "category"
            binding.newsTitle.text = newsData?.title
            binding.dateView.text = newsData?.publishedAt
            Glide.with(binding.root.context).load(newsData?.urlToImage)
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
            newsData?.articles?.let { it[position] },
            favouritesNews
        )
        if (editPreference.checkPreferenceExist()) {
            favouritesNews = editPreference.getPreference()
        }
        holder.binding.favCheckBox.setOnClickListener {

            val news = newsData?.articles?.get(position)
            if (holder.binding.favCheckBox.isChecked) {
                news?.let { favouritesNews.add(it) }
                editPreference.addPreference(favouritesNews)
            } else {
                favouritesNews.removeAt(favouritesNews.indexOf(news))
                editPreference.addPreference(favouritesNews)
            }
        }
        holder.binding.newsCard.setOnClickListener {

            newsData?.articles?.get(position)?.let { onCardClick?.invoke(it) }

        }

    }

    override fun getItemCount(): Int {
        return newsData?.articles?.size ?: 0
    }
}


