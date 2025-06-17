package com.learning.tmdb_movie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learning.tmdb_movie.Util.IMAGE_BASE_URL
import com.learning.tmdb_movie.Util.roundToDecimal
import com.learning.tmdb_movie.databinding.ListItemMovieBinding
import com.learning.tmdb_movie.model.MovieEntityModel
import com.learning.tmdb_movie.model.Favourite.FavouriteResponse
import kotlin.math.round


@SuppressLint("NotifyDataSetChanged")

class MovieListAdapter(
    private var movieList: List<MovieEntityModel>,
    private var favouriteList: List<FavouriteResponse>,
    private val onItemClick: (movieId: Int) -> Unit,
    private val toggleFavouriteClick: (movieFav: FavouriteResponse) -> Unit
) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(
        private val binding: ListItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(movie: MovieEntityModel) {
            // Set movie data
            binding.tvTitle.text = movie.title
            binding.tvRating.text = "${movie.voteAverage!!.roundToDecimal(1)}%"

            Glide.with(binding.root.context)
                .load(IMAGE_BASE_URL + movie.posterPath)
                .into(binding.ivPoster)

            // Set favorite state
            binding.tbFavorite.setOnCheckedChangeListener(null)
            binding.tbFavorite.isChecked = favouriteList.any { it.movieId == movie.id }

            // Set click listeners
            binding.cvMain.setOnClickListener { movie.id?.let(onItemClick) }
            binding.tbFavorite.setOnCheckedChangeListener { _, isChecked ->
                movie.id?.let { id ->
                    toggleFavouriteClick(FavouriteResponse(id, isFavourite = isChecked))
                }
            }
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

    fun updateMovieList(newList: List<MovieEntityModel>) {
        movieList = newList
        notifyDataSetChanged()
    }

    fun updateFavourites(newFavourites: List<FavouriteResponse>) {
        favouriteList = newFavourites
        notifyDataSetChanged()
    }

}