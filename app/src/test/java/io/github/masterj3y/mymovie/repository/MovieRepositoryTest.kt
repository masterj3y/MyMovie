package io.github.masterj3y.mymovie.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.github.masterj3y.mymovie.MainCoroutinesRule
import io.github.masterj3y.mymovie.movie.MovieDao
import io.github.masterj3y.mymovie.movie.MovieRepository
import io.github.masterj3y.mymovie.movie.MovieService
import io.github.masterj3y.mymovie.movie.details.MovieDetails
import io.github.masterj3y.mymovie.network.ApiUtil.getCall
import io.github.masterj3y.utils.MockUtils.mockedMovieDetails
import io.github.masterj3y.utils.MockUtils.mockedSearchMovieResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieRepositoryTest {

    private val service: MovieService = mock()
    private val dao: MovieDao = mock()
    private lateinit var repository: MovieRepository

    @get:Rule
    @ExperimentalCoroutinesApi
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutor = InstantTaskExecutorRule()

    @Before
    fun setup() {
        repository = MovieRepository(service, dao)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun searchMovieFromNetwork(): Unit = runBlocking {
        val mockedSearchMovieResult = mockedSearchMovieResult()
        val apiResponse = getCall(mockedSearchMovieResult).execute()

        whenever(service.search("Interstellar")).thenReturn(apiResponse)

        repository.search("Interstellar", {}, {})
        verify(service).search("Interstellar")

        Unit
    }

    @Test
    @ExperimentalCoroutinesApi
    fun getMovieDetails(): Unit = runBlocking {
        val observer: Observer<MovieDetails?> = mock()
        val mockedMovieDetails = mockedMovieDetails()
        val apiResponse = getCall(mockedMovieDetails).execute()

        whenever(dao.findMovieById("movie-1")).thenReturn(MutableLiveData(mockedMovieDetails))
        whenever(service.movieDetails("movie-1")).thenReturn(apiResponse)

        val loadData =
            MutableLiveData(repository.getMovieDetails(mockedMovieDetails.movieId, {}, {}).value)
        verify(dao).findMovieById("movie-1")
        verify(service).movieDetails("movie-1")

        loadData.observeForever(observer)

        val updatedData = MutableLiveData(mockedMovieDetails())
        whenever(dao.findMovieById("movie-1")).thenReturn(updatedData)

        loadData.postValue(updatedData.value)
        verify(observer, atLeastOnce()).onChanged(updatedData.value)

        loadData.removeObserver(observer)
    }
}