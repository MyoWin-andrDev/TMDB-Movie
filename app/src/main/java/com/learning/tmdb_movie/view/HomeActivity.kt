package com.learning.tmdb_movie.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import arrow.core.Either
import com.learning.tmdb_movie.Util.INTENT_ID
import com.learning.tmdb_movie.Util.showToast
import com.learning.tmdb_movie.adapter.MovieListAdapter
import com.learning.tmdb_movie.databinding.ActivityHomeBinding
import com.learning.tmdb_movie.model.Favourite.FavouriteResponse
import com.learning.tmdb_movie.model.MovieEntityModel
import com.learning.tmdb_movie.viewmodel.FavouriteViewModel
import com.learning.tmdb_movie.viewmodel.MovieViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val movieViewModel: MovieViewModel by viewModels()
    private val favouriteViewModel : FavouriteViewModel by viewModels()
    private val adapters = mutableListOf<MovieListAdapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObservers()
        loadData()
    }

    private fun loadData() {
        lifecycleScope.launchWhenStarted {
            movieViewModel.apply {
                getPopularList()
                getNowPlayingList()
                getUpComingList()
            }
        }
    }

    private fun setupObservers() {
        movieViewModel.popularList.observe(this) { movies ->
            createOrUpdateAdapter(movies, binding.rvPopularMovies.rvMain)
        }

        movieViewModel.nowPlayingList.observe(this) { movies ->
            createOrUpdateAdapter(movies, binding.rvNowPlayingMovies.rvMain)
        }

        movieViewModel.upComingList.observe(this) { movies ->
            createOrUpdateAdapter(movies, binding.rvUpcomingMovies.rvMain)
        }

        favouriteViewModel.favouriteList.observe(this) { response ->
            response.fold(
                { error -> showToast(error) },
                { favList -> updateAllAdapters(favList) }
            )
        }
    }

    private fun createOrUpdateAdapter(
        movies: List<MovieEntityModel>,
        recyclerView: RecyclerView
    ) {
        val adapter = recyclerView.adapter as? MovieListAdapter ?: createAdapter(movies)
        adapter.updateMovieList(movies)
        if (recyclerView.adapter == null) {
            recyclerView.adapter = adapter
            adapters.add(adapter)
        }
    }

    private fun createAdapter(movieList: List<MovieEntityModel>): MovieListAdapter {
        return MovieListAdapter(
            movieList = movieList,
            favouriteList = (favouriteViewModel.favouriteList.value as? Either.Right)?.value ?: emptyList(),
            onItemClick = { id -> navigateToDetail(id) },
            toggleFavouriteClick = { movieFav ->
                favouriteViewModel.toggleFavourite(movieFav)
            }
        )
    }

    private fun updateAllAdapters(favList: List<FavouriteResponse>) {
        adapters.forEach { adapter ->
            adapter.updateFavourites(favList)
        }
    }

    private fun navigateToDetail(id: Int) {
        Intent(this, DetailActivity::class.java).apply {
            putExtra(INTENT_ID, id)
            startActivity(this)
        }
    }
}