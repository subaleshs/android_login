package com.example.loginapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapp.databinding.NewsRecylcerLayoutBinding

class NewsAdapter(private val newsList: ArrayList<NewsTitle>): RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(binding: NewsRecylcerLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        private val binding = binding

        fun bind(newsData: NewsTitle){
            val (title, author, date) = newsData

            binding.newsTitle.text = title
            binding.authorNameView.text = author
            binding.dateView.text = date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val viewBinding = NewsRecylcerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}


