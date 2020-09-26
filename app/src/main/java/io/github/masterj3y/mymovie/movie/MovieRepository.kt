package io.github.masterj3y.mymovie.movie

import io.github.masterj3y.mymovie.core.platform.CacheNetworkBoundRepository
import io.github.masterj3y.mymovie.core.platform.NetworkBoundRepository
import io.github.masterj3y.mymovie.movie.details.MovieDetails
import io.github.masterj3y.mymovie.movie.search.SearchMovieResponse
import io.github.masterj3y.mymovie.movie.watchlist.WatchlistStatusLabel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class MovieRepository @Inject constructor(
    private val service: MovieService,
    private val dao: MovieDao
) {

    suspend fun search(
        title: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ): Flow<SearchMovieResponse> =
        withContext(IO) {
            object : NetworkBoundRepository<SearchMovieResponse>(onSuccess, onError) {
                override suspend fun fetchFromRemote() = service.search(title)

            }.asFlow()
        }

    suspend fun getMovieDetails(
        movieId: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ): Flow<MovieDetails?> =
        withContext(IO) {
            object : CacheNetworkBoundRepository<MovieDetails?, MovieDetails?>(onSuccess, onError) {
                override suspend fun saveRemoteData(response: MovieDetails?) {
                    response?.let { dao.insertMovie(it) }
                }

                override fun fetchFromLocal(): Flow<MovieDetails?> = dao.findMovieById(movieId)

                override suspend fun fetchFromRemote() = service.movieDetails(movieId)

            }.asFlow()
        }

    suspend fun addToWatchlist(movieId: String) = withContext(IO) {
        dao.addToWatchList(movieId)
    }

    suspend fun removeFromWatchlist(movieId: String) = withContext(IO) {
        dao.removeFromWatchlist(movieId)
    }

    suspend fun getWatchlist(): Flow<List<MovieDetails>> = withContext(IO) {
        dao.findWatchlist()
    }

    suspend fun getWatchlistSortByWatchStatus(watchStatus: WatchlistStatusLabel): Flow<List<MovieDetails>> =
        withContext(IO) {
            dao.findWatchlistSortByWatchStatus(watchStatus.toString())
        }

    suspend fun changeWatchStatus(movieId: String, status: WatchlistStatusLabel) = withContext(IO) {
        dao.changeMovieWatchStatus(movieId, status.toString())
    }
}