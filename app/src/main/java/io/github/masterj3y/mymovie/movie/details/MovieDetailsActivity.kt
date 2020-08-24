package io.github.masterj3y.mymovie.movie.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.masterj3y.mymovie.R
import io.github.masterj3y.mymovie.core.extension.*
import io.github.masterj3y.mymovie.core.platform.BaseActivity
import io.github.masterj3y.mymovie.databinding.ActivityMovieDetailsBinding

@AndroidEntryPoint
class MovieDetailsActivity : BaseActivity<ActivityMovieDetailsBinding>() {

    private val viewModel: MovieDetailsViewModel by viewModels()

    private val param: MovieDetailsParam? by lazy {
        intent.getParcelableExtra<MovieDetailsParam>(MOVIE_ID_KEY)
    }

    override fun layoutRes() = R.layout.activity_movie_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply {
            lifecycleOwner = this@MovieDetailsActivity
            viewModel = this@MovieDetailsActivity.viewModel
            param = this@MovieDetailsActivity.param

            appBarLayout.onCollapsingListener(::onCollapsingLayoutListener)
        }

        param?.let { viewModel.fetchMovieDetails(it.id) }
    }

    private fun onCollapsingLayoutListener(state: State) = with(binding.movieDetailsTitle) {
        println(state.toString())
        when (state) {
            State.COLLAPSED -> if (isVisible()) gone()
            State.EXPANDED -> if (!isVisible()) visible()
        }
    }

    companion object {

        private const val MOVIE_ID_KEY = "key:movie-id"

        fun start(context: Context, param: MovieDetailsParam) {
            val intent = Intent(context, MovieDetailsActivity::class.java).apply {
                putExtra(MOVIE_ID_KEY, param)
            }
            context.startActivity(intent)
        }
    }
}