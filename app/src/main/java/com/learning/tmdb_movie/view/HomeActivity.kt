package com.learning.tmdb_movie.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.learning.tmdb_movie.adapter.MovieListAdapter
import com.learning.tmdb_movie.databinding.ActivityHomeBinding
import com.learning.tmdb_movie.model.dto.response.movie.MovieItemModel
import com.learning.tmdb_movie.util.INTENT_ID
import com.learning.tmdb_movie.viewmodel.MovieViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val movieViewModel: MovieViewModel by viewModels()
    private val adapters = mutableListOf<MovieListAdapter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeMovieLists()
    }

    override fun onResume() {
        super.onResume()
        movieViewModel.initData(this)

    }

    private fun observeMovieLists() {
        observeMovieSection(movieViewModel.popularList, binding.rvPopularMovies.rvMain)
        observeMovieSection(movieViewModel.nowPlayingList, binding.rvNowPlayingMovies.rvMain)
        observeMovieSection(movieViewModel.upComingList, binding.rvUpcomingMovies.rvMain)
    }

    private fun observeMovieSection(
        liveData: LiveData<List<MovieItemModel>>,
        recyclerView: RecyclerView
    ) {
        liveData.observe(this) { movies ->
            val adapter = (recyclerView.adapter as? MovieListAdapter)
                ?: MovieListAdapter(
                    movieList = movies,
                    onFavClick = { movieViewModel.onFavClick(this@HomeActivity, it) },
                    onItemClick = ::navigateToDetail
                ).also {
                    recyclerView.adapter = it
                    adapters.add(it)
                }

            adapter.updateMovieList(movies)
        }
    }


    private fun navigateToDetail(id: Int) {
        startActivity(
            Intent(this, DetailActivity::class.java).apply {
                putExtra(INTENT_ID, id)
            }
        )
    }
}