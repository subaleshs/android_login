package com.example.loginapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginapp.NewsData
import com.example.loginapp.NewsContent
import com.example.loginapp.R
import com.example.loginapp.databinding.NewsRecylcerLayoutBinding

class NewsAdapter(private val newsData: NewsData) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    var onCardClick: ((NewsContent) -> Unit)? = null

    lateinit var context: Context

    class NewsViewHolder(val binding: NewsRecylcerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(newsData: NewsContent, category: String, context: Context) {
            val (title, date, content, url) = newsData

            binding.category.text = category
            binding.newsTitle.text = title
            binding.dateView.text = date
            Log.d("ur", url)
            Glide.with(context).load(url)
                .error(R.drawable.news)
                .into(binding.newsPicture)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val viewBinding =
            NewsRecylcerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return NewsViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        Log.d("pos", position.toString())
        holder.bind(newsData.data[position], newsData.category, context)
        holder.binding.newsCard.setOnClickListener {

            onCardClick?.invoke(newsData.data[position])

        }

    }

    override fun getItemCount(): Int {
        return newsData.data.size
    }
}


