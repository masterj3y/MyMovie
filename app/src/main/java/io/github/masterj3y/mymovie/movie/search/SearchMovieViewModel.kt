package io.github.masterj3y.mymovie.movie.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import io.github.masterj3y.mymovie.core.platform.BaseViewModel
import io.github.masterj3y.mymovie.movie.MovieRepository

class SearchMovieViewModel @ViewModelInject constructor(private val repository: MovieRepository) :
    BaseViewModel() {

    private val searchMovie = MutableLiveData<String>()
    val searchMovieResult: LiveData<SearchMovieResponse>

    val loading = MutableLiveData<Boolean>(false)

    init {
        searchMovieResult = searchMovie.switchMap {
            launchOnViewModelScope {
                loading()
                repository.search(it, { loading(false) }, { loading(false) })
            }
        }
    }

    fun search(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.isNotEmpty())
            searchMovie.value = s.toString()
    }

    private fun loading(visible: Boolean = true) = loading.postValue(visible)
}