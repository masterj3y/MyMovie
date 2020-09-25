package io.github.masterj3y.mymovie.movie.watchlist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import io.github.masterj3y.mymovie.core.platform.BaseViewModel
import io.github.masterj3y.mymovie.movie.MovieRepository
import io.github.masterj3y.mymovie.movie.details.MovieDetails
import io.github.masterj3y.mymovie.movie.watchlist.WatchlistStatusLabel.NOT_WATCHED
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class WatchlistViewModel @ViewModelInject constructor(private val repository: MovieRepository) :
    BaseViewModel() {

    private val sortByWatchStatus = MutableLiveData(NOT_WATCHED)
    val watchList: LiveData<List<MovieDetails>> = sortByWatchStatus.switchMap {
        launchOnViewModelScope {
            repository.getWatchlist(it.toString())
        }
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

    fun sortWatchlist(sortByWatchStatus: WatchlistStatusLabel) {
        this.sortByWatchStatus.value = sortByWatchStatus
    }
}