package io.github.masterj3y.mymovie.movie.watchlist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import io.github.masterj3y.mymovie.core.platform.BaseViewModel
import io.github.masterj3y.mymovie.movie.MovieRepository
import io.github.masterj3y.mymovie.movie.details.MovieDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class WatchlistViewModel @ViewModelInject constructor(private val repository: MovieRepository) :
    BaseViewModel() {

    private val channel = Channel<Boolean>()

    private var _watchlist: LiveData<List<MovieDetails>> = MutableLiveData()
    val watchlist: LiveData<List<MovieDetails>> = liveData {
        channel.consumeEach {
            emitSource(_watchlist)
        }
    }

    init {
        // Primitive Source
        switchSource { repository.getWatchlist() }
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

    fun findWatchlist() = switchSource {
        repository.getWatchlist()
    }

    fun findWatchlistSortByWatchStatus(sortByWatchStatus: WatchlistStatusLabel) = switchSource {
        repository.getWatchlistSortByWatchStatus(sortByWatchStatus)
    }

    fun findWatchlistFilterByWatchStatus(filterByWatchStatus: WatchlistStatusLabel) = switchSource {
        repository.getWatchlistFilterByWatchStatus(filterByWatchStatus)
    }

    private inline fun switchSource(crossinline block: suspend () -> Flow<List<MovieDetails>>) =
        viewModelScope.launch {
            _watchlist = launchOnViewModelScope(block)
            reloadWatchlistSource()
        }

    private suspend fun reloadWatchlistSource() = channel.send(true)
}