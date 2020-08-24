package io.github.masterj3y.mymovie.movie.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import io.github.masterj3y.mymovie.core.platform.BaseViewModel
import io.github.masterj3y.mymovie.movie.MovieRepository

class MovieDetailsViewModel @ViewModelInject constructor(repository: MovieRepository) :
    BaseViewModel() {

    private val fetchMovieDetails = MutableLiveData<String>()
    val movieDetails: LiveData<MovieDetails>

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

    private fun loading(visible: Boolean = true) = loading.postValue(visible)
}