package com.learning.tmdb_movie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learning.tmdb_movie.databinding.ListItemMovieBinding
import com.learning.tmdb_movie.model.CustomMovieModel

class CustomMovieAdapter(val movieList : List<CustomMovieModel>) : RecyclerView.Adapter<CustomMovieAdapter.CustomMovieViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomMovieViewHolder = CustomMovieViewHolder(ListItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: CustomMovieViewHolder,
        position: Int
    ){
       holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size

    inner class CustomMovieViewHolder (val binding : ListItemMovieBinding) : RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun bind(movie : CustomMovieModel) = with(binding){
            tvTitle.text = movie.title
            tvRating.text = "${movie.voteAverage}%"
            Glide.with(root.context)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .into(ivPoster)
        }
    }
}
