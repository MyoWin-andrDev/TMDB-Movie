package com.learning.tmdb_movie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.learning.tmdb_movie.Util.IMAGE_BASE_URL
import com.learning.tmdb_movie.databinding.ListItemMovieBinding
import com.learning.tmdb_movie.model.MovieEntityModel
import com.learning.tmdb_movie.model.Favourite.FavouriteResponse

@SuppressLint("NotifyDataSetChanged")
class MovieListAdapter(
    private var movieList: List<MovieEntityModel>,
    private var favouriteList: List<FavouriteResponse>,
    private val onItemClick: (movieId: Int) -> Unit,
    private val toggleFavouriteClick: (movieFav: FavouriteResponse) -> Unit
) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(ListItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))

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

    private fun isFavourite(movie: MovieEntityModel): Boolean =
        favouriteList.any { it.movieId == movie.id }

    inner class MovieViewHolder(private val binding: ListItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(movie: MovieEntityModel) {
            with(binding) {
                tvTitle.text = movie.title
                tvRating.text = "${movie.voteAverage}%"

                Glide.with(root.context)
                    .load(IMAGE_BASE_URL + movie.posterPath)
                    .into(ivPoster)

                tbFavorite.setOnCheckedChangeListener(null) // Clear previous listener
                tbFavorite.isChecked = isFavourite(movie) // Set checked state

                cvMain.setOnClickListener {
                    movie.id?.let { id -> onItemClick(id) }
                }

                tbFavorite.setOnCheckedChangeListener { _, isChecked ->
                    movie.id?.let { id ->
                        toggleFavouriteClick(
                            FavouriteResponse(
                                movieId = id,
                                isFavourite = isChecked
                            )
                        )
                    }
                }
            }
        }
    }
}