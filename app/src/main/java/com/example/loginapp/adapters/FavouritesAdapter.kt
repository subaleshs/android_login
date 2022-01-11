package com.example.loginapp.adapters

import android.content.Context.MODE_PRIVATE
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginapp.R
import com.example.loginapp.databinding.NewsRecylcerLayoutBinding
import com.example.loginapp.model.NewsContent
import com.example.loginapp.utils.EditPreference
import com.google.firebase.auth.FirebaseAuth

class FavouritesAdapter: RecyclerView.Adapter<FavouritesAdapter.FavoritesViewHolder>() {

    private var favouritesArray: MutableList<NewsContent> = arrayListOf()
    var emptyFavourites: (()->Unit)? = null
    var expandNews: ((NewsContent)->Unit)? = null

    fun getFavNews(favouritesArray: MutableList<NewsContent>) {
        this.favouritesArray = favouritesArray
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
        holder.bind(favouritesArray[position])

        holder.binding.favCheckBox.setOnClickListener {
            favouritesArray.removeAt(position)
            if (favouritesArray.size ==0) {
                emptyFavourites?.invoke()
            }
            notifyDataSetChanged()
            val preference = holder.binding.root.context.getSharedPreferences(FirebaseAuth.getInstance().currentUser?.uid.toString(), MODE_PRIVATE)
            val editPreference = EditPreference(preference)
            editPreference.addPreference(favouritesArray)
        }

        holder.binding.newsCard.setOnClickListener {
            expandNews?.invoke(favouritesArray[position])
        }
    }

    override fun getItemCount(): Int {
        return favouritesArray.size
    }
}