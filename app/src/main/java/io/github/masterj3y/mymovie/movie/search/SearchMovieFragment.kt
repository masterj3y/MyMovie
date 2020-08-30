package io.github.masterj3y.mymovie.movie.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.masterj3y.mymovie.R
import io.github.masterj3y.mymovie.core.platform.BaseFragment
import io.github.masterj3y.mymovie.databinding.FragmentSearchMovieBinding
import io.github.masterj3y.mymovie.movie.details.MovieDetailsActivity
import io.github.masterj3y.mymovie.movie.details.MovieDetailsParam

@AndroidEntryPoint
class SearchMovieFragment :
    BaseFragment<FragmentSearchMovieBinding>(R.layout.fragment_search_movie) {

    private val viewModel: SearchMovieViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@SearchMovieFragment.viewModel
            adapter = SearchMovieAdapter(::onMovieItemClicked)
        }
    }

    private fun onMovieItemClicked(movieItem: SearchMovieItem, position: Int) =
        MovieDetailsActivity.start(requireContext(), MovieDetailsParam.from(movieItem))
}