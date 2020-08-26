package io.github.masterj3y.mymovie.movie

import androidx.lifecycle.LiveData
import io.github.masterj3y.mymovie.core.platform.NetworkClient
import io.github.masterj3y.mymovie.movie.details.MovieDetails
import io.github.masterj3y.mymovie.movie.search.SearchMovieResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val service: MovieService,
    private val dao: MovieDao
) {

    suspend fun search(
        title: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ): LiveData<SearchMovieResponse> =
        withContext(IO) {
            object : NetworkClient<SearchMovieResponse>() {
                override suspend fun fetchFromNetwork(): Response<SearchMovieResponse> {
                    return service.search(title)
                }
            }.asLiveData(onSuccess, onError)
        }

    suspend fun getMovieDetails(
        movieId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ): LiveData<MovieDetails> =
        withContext(IO) {
            object : NetworkClient<MovieDetails>() {
                override suspend fun fetchFromNetwork(): Response<MovieDetails> {
                    return service.movieDetails(movieId)
                }
            }.fetch({
                dao.insertMovie(it)
                onSuccess()
            }, onError)
            dao.findMovieById(movieId)
        }

    suspend fun addToWatchlist(movieId: String) = withContext(IO) {
        dao.addToWatchList(movieId)
    }

    suspend fun removeFromWatchlist(movieId: String) = withContext(IO) {
        dao.removeFromWatchlist(movieId)
    }
}