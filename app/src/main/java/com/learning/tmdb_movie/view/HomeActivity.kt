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
    private val favouriteViewModel: FavouriteViewModel by viewModels()
    private val adapters = mutableListOf<MovieListAdapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeBinding()
        setupObservers()
        loadInitialData()
    }

    private fun initializeBinding() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun loadInitialData() {
        lifecycleScope.launchWhenStarted {
            with(movieViewModel) {
                getPopularList()
                getNowPlayingList()
                getUpComingList()
            }
        }
    }

    private fun setupObservers() {
        observeMovieLists()
        observeFavourites()
    }

    private fun observeMovieLists() {
        movieViewModel.popularList.observe(this) { updateMovieList(it, binding.rvPopularMovies.rvMain) }
        movieViewModel.nowPlayingList.observe(this) { updateMovieList(it, binding.rvNowPlayingMovies.rvMain) }
        movieViewModel.upComingList.observe(this) { updateMovieList(it, binding.rvUpcomingMovies.rvMain) }
    }

    private fun observeFavourites() {
        favouriteViewModel.favouriteList.observe(this) { response ->
            response.fold(
                ::showToast,
                ::updateAllAdapters
            )
        }
    }

    private fun updateMovieList(movies: List<MovieEntityModel>, recyclerView: RecyclerView) {
        val adapter = getOrCreateAdapter(movies, recyclerView)
        adapter.updateMovieList(movies)
    }

    private fun getOrCreateAdapter(
        movies: List<MovieEntityModel>,
        recyclerView: RecyclerView
    ): MovieListAdapter {
        return (recyclerView.adapter as? MovieListAdapter) ?: createNewAdapter(movies, recyclerView)
    }

    private fun createNewAdapter(
        movieList: List<MovieEntityModel>,
        recyclerView: RecyclerView
    ): MovieListAdapter {
        return MovieListAdapter(
            movieList = movieList,
            favouriteList = getCurrentFavourites(),
            onItemClick = ::navigateToDetail,
            toggleFavouriteClick = favouriteViewModel::toggleFavourite
        ).also {
            recyclerView.adapter = it
            adapters.add(it)
        }
    }

    private fun getCurrentFavourites(): List<FavouriteResponse> {
        return (favouriteViewModel.favouriteList.value as? Either.Right)?.value ?: emptyList()
    }

    private fun updateAllAdapters(favList: List<FavouriteResponse>) {
        adapters.forEach { it.updateFavourites(favList) }
    }

    private fun navigateToDetail(id: Int) {
        Intent(this, DetailActivity::class.java).apply {
            putExtra(INTENT_ID, id)
            startActivity(this)
        }
    }
}