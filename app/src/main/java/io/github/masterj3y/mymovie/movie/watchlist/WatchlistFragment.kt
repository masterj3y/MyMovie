package io.github.masterj3y.mymovie.movie.watchlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.masterj3y.mymovie.R
import io.github.masterj3y.mymovie.core.platform.BaseFragment
import io.github.masterj3y.mymovie.databinding.FragmentWatchlistBinding
import io.github.masterj3y.mymovie.movie.details.MovieDetails
import io.github.masterj3y.mymovie.movie.details.MovieDetailsActivity
import io.github.masterj3y.mymovie.movie.details.MovieDetailsParam
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
class WatchlistFragment : BaseFragment<FragmentWatchlistBinding>(R.layout.fragment_watchlist) {

    private val viewModel: WatchlistViewModel by viewModels()

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@WatchlistFragment.viewModel
            adapter = WatchlistAdapter(::onMovieItemClicked)
        }
    }


    private fun onMovieItemClicked(movieItem: MovieDetails, position: Int) =
        MovieDetailsActivity.start(requireContext(), MovieDetailsParam.from(movieItem))
}