package io.github.masterj3y.mymovie.core.platform

import androidx.annotation.WorkerThread
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response

@ExperimentalCoroutinesApi
abstract class NetworkBoundRepository<REQUEST>(
    private val onSuccess: () -> Unit,
    private val onError: (String) -> Unit
) {

    fun asFlow() = flow<REQUEST> {

        val apiResponse = fetchFromRemote()
        val body = apiResponse.body()
        if (apiResponse.isSuccessful && body != null) {
            emit(body)
            onSuccess()
        } else {
            onError(apiResponse.message())
        }
    }.catch { e ->
        e.message?.let { onError(it) }
    }

    @WorkerThread
    abstract suspend fun fetchFromRemote(): Response<REQUEST>
}