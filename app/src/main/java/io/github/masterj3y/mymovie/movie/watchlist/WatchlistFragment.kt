package io.github.masterj3y.mymovie.movie.watchlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.github.masterj3y.mymovie.R
import io.github.masterj3y.mymovie.core.platform.BaseFragment
import io.github.masterj3y.mymovie.databinding.FragmentWatchlistBinding
import io.github.masterj3y.mymovie.dialog.action.ActionBottomSheetDialog
import io.github.masterj3y.mymovie.dialog.action.ActionBottomSheetDialogItem
import io.github.masterj3y.mymovie.dialog.action.actionBottomSheetDialog
import io.github.masterj3y.mymovie.movie.details.MovieDetails
import io.github.masterj3y.mymovie.movie.details.MovieDetailsActivity
import io.github.masterj3y.mymovie.movie.details.MovieDetailsParam
import io.github.masterj3y.mymovie.movie.watchlist.WatchlistStatusLabel.NOT_WATCHED
import io.github.masterj3y.mymovie.movie.watchlist.WatchlistStatusLabel.WATCHED
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class WatchlistFragment : BaseFragment<FragmentWatchlistBinding>(R.layout.fragment_watchlist),
    WatchlistAdapter.OnItemClickListener {

    private val viewModel: WatchlistViewModel by viewModels()

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@WatchlistFragment.viewModel
            adapter = WatchlistAdapter(this@WatchlistFragment)

            sortAndFilter.setOnClickListener { showSortAndFilterDialog() }
        }
    }

    override fun onItemClicked(movieDetails: MovieDetails, position: Int) =
        MovieDetailsActivity.start(requireContext(), MovieDetailsParam.from(movieDetails))

    override fun onChangeStatusActionClicked(movieDetails: MovieDetails, position: Int) =
        showStatusDialog(movieDetails.movieId)

    override fun onDeleteActionClicked(movieDetails: MovieDetails, position: Int) =
        viewModel.removeFromWatchlist(movieDetails.movieId)

    private fun showStatusDialog(movieId: String) =
        actionBottomSheetDialog {
            title(R.string.watch_list_status_dialog_title)
            items = watchStatusItems(movieId, this)
        }.show()

    private fun watchStatusItems(movieId: String, dialog: ActionBottomSheetDialog) = listOf(
        ActionBottomSheetDialogItem(R.drawable.ic_check_circle, R.string.movie_watched) {
            viewModel.changeWatchStatus(movieId, WATCHED)
            dialog.dismiss()
        },
        ActionBottomSheetDialogItem(R.drawable.ic_times_circle, R.string.movie_not_watched) {
            viewModel.changeWatchStatus(movieId, NOT_WATCHED)
            dialog.dismiss()
        }
    )

    private fun showSortAndFilterDialog() {
        actionBottomSheetDialog {
            title(R.string.watchlist_sort_and_filter)
            items = sortAndFilterItems(this)
        }.show()
    }

    private fun sortAndFilterItems(dialog: ActionBottomSheetDialog) = listOf(
        ActionBottomSheetDialogItem(text = R.string.sort_watchlist_by_not_watched_movies) {
            viewModel.sortWatchlist(NOT_WATCHED)
            dialog.dismiss()
        },
        ActionBottomSheetDialogItem(text = R.string.sort_watchlist_by_watched_movies) {
            viewModel.sortWatchlist(WATCHED)
            dialog.dismiss()
        }
    )
}