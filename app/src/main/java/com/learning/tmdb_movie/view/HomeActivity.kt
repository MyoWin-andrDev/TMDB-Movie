package com.learning.tmdb_movie.view

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.learning.tmdb_movie.adapter.MovieAdapter
import com.learning.tmdb_movie.databinding.ActivityHomeBinding
import com.learning.tmdb_movie.viewmodel.MovieViewModel
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    private val viewModel : MovieViewModel by viewModels()
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
            binding.rvPopularMovies.adapter = MovieAdapter(it)
            Log.d("PopularList", it.toString())
        }
        viewModel.nowPlayingList.observe(this){
            binding.rvNowPlayingMovies.adapter = MovieAdapter(it)
        }
        viewModel.upComingList.observe(this){
            binding.rvUpcomingMovies.adapter = MovieAdapter(it)
        }
        viewModel.errorStatus.observe(this){
            Log.d("Error", it.toString())
        }
    }
}