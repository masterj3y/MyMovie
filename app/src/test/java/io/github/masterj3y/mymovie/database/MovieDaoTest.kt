package io.github.masterj3y.mymovie.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.github.masterj3y.mymovie.movie.MovieDao
import io.github.masterj3y.mymovie.movie.details.MovieDetails
import io.github.masterj3y.utils.MockUtils.mockedMovieDetails
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
@ExperimentalCoroutinesApi
class MovieDaoTest : TestDatabase() {

    private lateinit var dao: MovieDao

    @get:Rule
    var instantExecutor = InstantTaskExecutorRule()

    @Before
    fun initDao() {
        dao = db.movieDao()
    }

    @Test
    fun insertMovieDetails() {
        val observer: Observer<MovieDetails> = mock()
        val mockedData = mockedMovieDetails()
        val loadData = dao.findMovieById("movie-1")

        loadData.observeForever(observer)

        dao.insertMovie(mockedData)

        verify(observer).onChanged(mockedData)
        loadData.removeObserver(observer)
    }

    @Test
    fun addMovieToWatchList() {
        val observer: Observer<MovieDetails> = mock()
        val mockedMovieDetails = mockedMovieDetails()
        mockedMovieDetails.inWatchlist = false
        dao.insertMovie(mockedMovieDetails)
        dao.addToWatchList(mockedMovieDetails.movieId)

        val updatedData = dao.findMovieById(mockedMovieDetails.movieId)
        updatedData.observeForever(observer)
        assertEquals(updatedData.value?.inWatchlist, true)

        updatedData.removeObserver(observer)
    }

    @Test
    fun removeMovieFromWatchList() {
        val observer: Observer<MovieDetails> = mock()
        val mockedMovieDetails = mockedMovieDetails()
        mockedMovieDetails.inWatchlist = true
        dao.insertMovie(mockedMovieDetails)
        dao.removeFromWatchlist(mockedMovieDetails.movieId)

        val updatedData = dao.findMovieById(mockedMovieDetails.movieId)
        updatedData.observeForever(observer)
        assertEquals(updatedData.value?.inWatchlist, false)

        updatedData.removeObserver(observer)
    }
}