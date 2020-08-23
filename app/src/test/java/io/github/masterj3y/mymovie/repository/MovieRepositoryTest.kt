package io.github.masterj3y.mymovie.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.github.masterj3y.mymovie.MainCoroutinesRule
import io.github.masterj3y.mymovie.movie.MovieRepository
import io.github.masterj3y.mymovie.movie.MovieService
import io.github.masterj3y.mymovie.network.ApiUtil.getCall
import io.github.masterj3y.utils.MockUtils.mockedSearchMovieResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieRepositoryTest {

    private val service: MovieService = mock()
    private lateinit var repository: MovieRepository

    @get:Rule
    @ExperimentalCoroutinesApi
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutor = InstantTaskExecutorRule()

    @Before
    fun setup() {
        repository = MovieRepository(service)
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
}