package com.example.loginapp.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.NewsData
import com.example.loginapp.NewsContent
import com.example.loginapp.databinding.NewsRecylcerLayoutBinding

class NewsAdapter(private val newsData: NewsData): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    lateinit var context: Context

    var onCardClick: ((NewsContent)-> Unit)? = null

    private val favNewsArrayList: ArrayList<Int> = ArrayList()

    interface ViewClickInterface{ fun expandNews(news: NewsContent)}

    class NewsViewHolder(val binding: NewsRecylcerLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(newsData: NewsContent, category: String){
            val (title, date) = newsData

            binding.category.text = category
            binding.newsTitle.text = title
            binding.dateView.text = date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val viewBinding = NewsRecylcerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        Log.d("pos",position.toString())
        holder.bind(newsData.data[position], newsData.category)
        val fm = holder.binding.root.context
        holder.binding.newsCard.setOnClickListener {

            onCardClick?.invoke(newsData.data[position])

        }

    }

    override fun getItemCount(): Int {
        return newsData.data.size
    }
}


