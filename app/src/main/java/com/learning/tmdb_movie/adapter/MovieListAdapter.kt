package com.learning.tmdb_movie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learning.tmdb_movie.R
import com.learning.tmdb_movie.databinding.ListItemMovieBinding
import com.learning.tmdb_movie.model.dto.response.movie.MovieItemModel
import com.learning.tmdb_movie.util.IMAGE_BASE_URL
import com.learning.tmdb_movie.util.roundToDecimal


@SuppressLint("NotifyDataSetChanged")

class MovieListAdapter(
    private var movieList: List<MovieItemModel>,
    private val onFavClick: (Int) -> Unit,
    private val onItemClick: (movieId: Int) -> Unit,
) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(
        private val binding: ListItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(movie: MovieItemModel) {
            // Set movie data
            binding.tvTitle.text = movie.title
            binding.tvRating.text = "${movie.voteAverage!!.roundToDecimal(1)}%"
            binding.ivFavorite.setOnClickListener {
                onFavClick(movie.id!!)
            }

            val colorRes =
                if (movie.isFavorite) R.color.red else R.color.grey
            binding.ivFavorite.imageTintList =
                ContextCompat.getColorStateList(binding.root.context, colorRes)

            Glide.with(binding.root.context)
                .load(IMAGE_BASE_URL + movie.posterPath)
                .into(binding.ivPoster)


            // Set click listeners
            binding.cvMain.setOnClickListener { movie.id?.let(onItemClick) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ListItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieList[position])
    }

    override fun getItemCount(): Int = movieList.size

    fun updateMovieList(newList: List<MovieItemModel>) {
        movieList = newList
        notifyDataSetChanged()
    }

}