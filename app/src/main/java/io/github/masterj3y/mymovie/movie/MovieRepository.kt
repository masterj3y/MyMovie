package io.github.masterj3y.mymovie.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import io.github.masterj3y.mymovie.core.platform.CacheNetworkBoundRepository
import io.github.masterj3y.mymovie.core.platform.NetworkBoundRepository
import io.github.masterj3y.mymovie.movie.details.MovieDetails
import io.github.masterj3y.mymovie.movie.search.SearchMovieResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val service: MovieService,
    private val dao: MovieDao
) {

    @ExperimentalCoroutinesApi
    suspend fun search(
        title: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ): LiveData<SearchMovieResponse> =
        withContext(IO) {
            object : NetworkBoundRepository<SearchMovieResponse>(onSuccess, onError) {
                override suspend fun fetchFromRemote() = service.search(title)

            }.asFlow().asLiveData()
        }

    @ExperimentalCoroutinesApi
    suspend fun getMovieDetails(
        movieId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ): LiveData<MovieDetails?> =
        withContext(IO) {
            object : CacheNetworkBoundRepository<MovieDetails?, MovieDetails?>(onSuccess, onError) {
                override suspend fun saveRemoteData(response: MovieDetails?) {
                    response?.let { dao.insertMovie(it) }
                }

                override fun fetchFromLocal(): Flow<MovieDetails?> = dao.findMovieById(movieId)

                override suspend fun fetchFromRemote() = service.movieDetails(movieId)

            }.asFlow().asLiveData()
        }

    suspend fun addToWatchlist(movieId: String) = withContext(IO) {
        dao.addToWatchList(movieId)
    }

    suspend fun removeFromWatchlist(movieId: String) = withContext(IO) {
        dao.removeFromWatchlist(movieId)
    }
}