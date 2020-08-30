package io.github.masterj3y.mymovie.movie.details

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import io.github.masterj3y.mymovie.core.platform.BaseViewModel
import io.github.masterj3y.mymovie.movie.MovieRepository
import kotlinx.coroutines.launch

class MovieDetailsViewModel @ViewModelInject constructor(private val repository: MovieRepository) :
    BaseViewModel() {

    private val fetchMovieDetails = MutableLiveData<String>()
    val movieDetails: LiveData<MovieDetails?>

    val loading = MutableLiveData<Boolean>(false)

    init {
        movieDetails = fetchMovieDetails.switchMap {
            launchOnViewModelScope {
                loading()
                repository.getMovieDetails(it, { loading(false) }, { loading(false) })
            }
        }
    }

    fun fetchMovieDetails(movieId: String) {
        fetchMovieDetails.value = movieId
    }

    fun addToWatchlist(view: View?, movieId: String) = viewModelScope.launch {
        repository.addToWatchlist(movieId)
    }

    fun removeFromWatchlist(view: View?, movieId: String) = viewModelScope.launch {
        repository.removeFromWatchlist(movieId)
    }

    private fun loading(visible: Boolean = true) = loading.postValue(visible)
}