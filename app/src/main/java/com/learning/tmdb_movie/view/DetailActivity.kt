package com.learning.tmdb_movie.view

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.learning.tmdb_movie.R
import com.learning.tmdb_movie.Util.IMAGE_BASE_URL
import com.learning.tmdb_movie.Util.INTENT_ID
import com.learning.tmdb_movie.Util.showToast
import com.learning.tmdb_movie.databinding.ActivityDetailBinding
import com.learning.tmdb_movie.model.MovieDetail.DetailResponse
import com.learning.tmdb_movie.viewmodel.DetailViewModel
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    private val viewModel : DetailViewModel by viewModels()
    private var id : Int = 0
    private var detailResponse : DetailResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadMovieData()
        observeMovieData()
    }

    private fun loadMovieData(){
        id = intent.getIntExtra(INTENT_ID, 0)
        Log.d("ID", id.toString())
        lifecycleScope.launch {
            viewModel.getDetailMovie(id)
        }
    }

    private fun observeMovieData(){
        viewModel.detailMovie.observe(this){ either ->
            either.fold(
                ifLeft = { errorMsg -> showToast(errorMsg)},
                ifRight = { details  ->
                    detailResponse = details
                    setupData()
                }
            )
        }
    }

    private fun setupData() = with(binding){
        detailResponse?.let { it ->
            //Backdrop Path
            Glide.with(this@DetailActivity)
                .load(IMAGE_BASE_URL + it.backdropPath)
                .into(ivBackdrop)
            //Title
            tvMovieTitle.text = it.title

        }
    }
}