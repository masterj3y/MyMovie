package io.github.masterj3y.mymovie.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.github.masterj3y.mymovie.MainCoroutinesRule
import io.github.masterj3y.mymovie.movie.MovieDao
import io.github.masterj3y.utils.MockUtils.mockedMovieDetails
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
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
    @ExperimentalCoroutinesApi
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutor = InstantTaskExecutorRule()

    @Before
    fun initDao() {
        dao = db.movieDao()
    }

    @Test
    fun insertMovieDetails() = runBlocking {
        val mockedData = mockedMovieDetails()
        dao.insertMovie(mockedData)
        val loadData = dao.findMovieById("movie-1")
        assertEquals(mockedData, loadData.toList().first())
    }

    @Test
    fun addMovieToWatchList() = runBlocking {
        val mockedMovieDetails = mockedMovieDetails()
        mockedMovieDetails.inWatchlist = false
        dao.insertMovie(mockedMovieDetails)
        dao.addToWatchList(mockedMovieDetails.movieId)

        val updatedData = dao.findMovieById(mockedMovieDetails.movieId)
        assertEquals(true, updatedData.first()?.inWatchlist)
    }

    @Test
    fun removeMovieFromWatchList() = runBlocking {
        val mockedMovieDetails = mockedMovieDetails()
        mockedMovieDetails.inWatchlist = true
        dao.insertMovie(mockedMovieDetails)
        dao.removeFromWatchlist(mockedMovieDetails.movieId)

        val updatedData = dao.findMovieById(mockedMovieDetails.movieId)
        assertEquals(false, updatedData.first()?.inWatchlist)
    }

    @Test
    fun getWatchlist() = runBlocking {
        val mockedWatchlist = listOf(
            mockedMovieDetails().copy(inWatchlist = true),
            mockedMovieDetails().copy(inWatchlist = false)
        )
        dao.insertMovie(*mockedWatchlist.toTypedArray())
        val actualWatchlist = dao.findWatchlist().toList()
        val expectedWatchlist = listOf(mockedMovieDetails().copy(inWatchlist = true))

        assertEquals(actualWatchlist, expectedWatchlist)
    }
}