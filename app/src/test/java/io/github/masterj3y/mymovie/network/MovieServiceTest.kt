package io.github.masterj3y.mymovie.network

import io.github.masterj3y.mymovie.MainCoroutinesRule
import io.github.masterj3y.mymovie.movie.MovieService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class MovieServiceTest : TestService<MovieService>() {

    private lateinit var service: MovieService

    @get:Rule
    @ExperimentalCoroutinesApi
    var coroutinesRule = MainCoroutinesRule()

    @Before
    fun initService() {
        service = createService(MovieService::class.java)
    }

    @Test
    @Throws(IOException::class)
    fun searchMovieFromNetwork() = runBlocking {
        enqueueResponse("search/response.json")
        val response = requireNotNull(service.search("interstellar"))
        val body = requireNotNull(response.body())
        mockWebServer.takeRequest()
        val actual = requireNotNull(body.result)
        assertEquals(actual.size, 10)
        val movie = actual[0]
        assertEquals(movie.id, "tt0816692")
        assertEquals(movie.title, "Interstellar")
        assertEquals(
            movie.poster,
            "https://m.media-amazon.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg"
        )
    }

    @Test
    @Throws(IOException::class)
    fun fetchMovieDetailsFromNetwork() = runBlocking {
        enqueueResponse("details/response.json")
        val response = requireNotNull(service.movieDetails("tt0109830"))
        val body = requireNotNull(response.body())
        mockWebServer.takeRequest()
        val actual = body
        assertEquals(actual.movieId, "tt0109830")
        assertEquals(actual.title, "Forrest Gump")
        assertEquals(
            actual.description,
            "The presidencies of Kennedy and Johnson, the events of Vietnam, Watergate and other historical events unfold through the perspective of an Alabama man with an IQ of 75, whose only desire is to be reunited with his childhood sweetheart."
        )
        assertEquals(
            actual.poster,
            "https://m.media-amazon.com/images/M/MV5BNWIwODRlZTUtY2U3ZS00Yzg1LWJhNzYtMmZiYmEyNmU1NjMzXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_SX300.jpg"
        )
    }
}