package io.github.masterj3y.mymovie.movie.watchlist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import io.github.masterj3y.mymovie.core.platform.BaseViewModel
import io.github.masterj3y.mymovie.movie.MovieRepository
import io.github.masterj3y.mymovie.movie.details.MovieDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class WatchlistViewModel @ViewModelInject constructor(private val repository: MovieRepository) :
    BaseViewModel() {

    private var findWatchlist = MutableLiveData(true)
    private val findWatchlistSortByWatchStatus = MutableLiveData<WatchlistStatusLabel>()
    private val findWatchlistFilterByWatchStatus = MutableLiveData<WatchlistStatusLabel>()

    private val unsortedWatchlist: LiveData<List<MovieDetails>>
    private val sortedWatchlist: LiveData<List<MovieDetails>>
    private val filteredWatchlist: LiveData<List<MovieDetails>>

    val watchlist = MediatorLiveData<List<MovieDetails>>()

    init {
        unsortedWatchlist = findWatchlist.switchMap {
            launchOnViewModelScope { repository.getWatchlist() }
        }
        sortedWatchlist = findWatchlistSortByWatchStatus.switchMap {
            launchOnViewModelScope { repository.getWatchlistSortByWatchStatus(it) }
        }
        filteredWatchlist = findWatchlistFilterByWatchStatus.switchMap {
            launchOnViewModelScope { repository.getWatchlistFilterByWatchStatus(it) }
        }

        watchlist.addSource(unsortedWatchlist) {
            watchlist.value = unsortedWatchlist.value
        }
        watchlist.addSource(sortedWatchlist) {
            watchlist.value = sortedWatchlist.value
        }
        watchlist.addSource(filteredWatchlist) {
            watchlist.value = filteredWatchlist.value
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

    fun findWatchlist() {
        this.findWatchlist.value = true
    }

    fun findWatchlistSortByWatchStatus(sortByWatchStatus: WatchlistStatusLabel) {
        this.findWatchlistSortByWatchStatus.value = sortByWatchStatus
    }

    fun findWatchlistFilterByWatchStatus(filterByWatchStatus: WatchlistStatusLabel) {
        this.findWatchlistFilterByWatchStatus.value = filterByWatchStatus
    }
}