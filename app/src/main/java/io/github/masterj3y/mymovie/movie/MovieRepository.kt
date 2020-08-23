package io.github.masterj3y.mymovie.movie

import androidx.lifecycle.LiveData
import io.github.masterj3y.mymovie.core.platform.NetworkClient
import io.github.masterj3y.mymovie.movie.search.SearchMovieResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(private val service: MovieService) {

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
}