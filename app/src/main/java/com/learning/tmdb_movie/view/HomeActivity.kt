package com.learning.tmdb_movie.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.learning.tmdb_movie.Util.INTENT_ID
import com.learning.tmdb_movie.adapter.MovieListAdapter
import com.learning.tmdb_movie.databinding.ActivityHomeBinding
import com.learning.tmdb_movie.repository.DetailRepository
import com.learning.tmdb_movie.viewmodel.DetailViewModel
import com.learning.tmdb_movie.viewmodel.MovieViewModel
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    private val viewModel : MovieViewModel by viewModels()
    private val detailViewModel : DetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            viewModel.apply {
                getPopularList()
                getNowPlayingList()
                getUpComingList()
            }
        }
        viewModel.popularList.observe(this){
            binding.rvPopularMovies.rvMain.adapter = MovieListAdapter(it){id -> navigateToDetail(id)}
        }
        viewModel.nowPlayingList.observe(this){
            binding.rvNowPlayingMovies.rvMain.adapter = MovieListAdapter(it){id -> navigateToDetail(id)}
        }
        viewModel.upComingList.observe(this){
            binding.rvUpcomingMovies.rvMain.adapter = MovieListAdapter(it){id -> navigateToDetail(id)}
        }
        viewModel.errorStatus.observe(this){
            Log.d("Error", it.toString())
        }

    }

    private fun navigateToDetail(id : Int){
        Intent(this@HomeActivity, DetailActivity::class.java).apply {
            putExtra(INTENT_ID , id)
            startActivity(this)
        }
    }
}