package io.github.masterj3y.mymovie.core.platform

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import retrofit2.Response

@ExperimentalCoroutinesApi
abstract class CacheNetworkBoundRepository<RESULT, REQUEST>(
    private val onSuccess: () -> Unit,
    private val onError: (String) -> Unit
) {

    fun asFlow() = flow {

        val localData = fetchFromLocal().first()
        if (localData == null) {
            val apiResponse = fetchFromRemote()
            val remoteData = apiResponse.body()
            if (apiResponse.isSuccessful && remoteData != null) {
                saveRemoteData(remoteData)
            } else {
                onError(apiResponse.message())
            }
        }
        onSuccess()
        emitAll(fetchFromLocal())
    }.catch { e ->
        e.message?.let { onError(it) }
    }

    @WorkerThread
    abstract suspend fun saveRemoteData(response: REQUEST)

    @MainThread
    abstract fun fetchFromLocal(): Flow<RESULT>

    @WorkerThread
    abstract suspend fun fetchFromRemote(): Response<REQUEST>
}