package io.github.masterj3y.mymovie.movie.watchlist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import io.github.masterj3y.mymovie.core.platform.BaseViewModel
import io.github.masterj3y.mymovie.movie.MovieRepository
import io.github.masterj3y.mymovie.movie.details.MovieDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class WatchlistViewModel @ViewModelInject constructor(private val repository: MovieRepository) :
    BaseViewModel() {

    @ExperimentalCoroutinesApi
    val watchList: LiveData<List<MovieDetails>> = launchOnViewModelScope {
        repository.getWatchlist()
    }

    fun removeFromWatchlist(movieId: String) {
        viewModelScope.launch {
            repository.removeFromWatchlist(movieId)
        }
    }

    fun changeWatchStatus(movieId: String, status: WatchlistStatusLabel) {
        viewModelScope.launch {
            repository.changeWatchStatus(movieId, status)
        }
    }
}