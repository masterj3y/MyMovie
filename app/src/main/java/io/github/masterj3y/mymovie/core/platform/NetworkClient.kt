package io.github.masterj3y.mymovie.core.platform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Response

abstract class NetworkClient<T> {

    suspend fun fetch(onSuccess: (T) -> Unit, onError: (String) -> Unit) {
        try {
            val apiResponse = fetchFromNetwork()
            val body = apiResponse.body()

            if (apiResponse.isSuccessful && body != null) {
                onSuccess(body)
            } else {
                onError(apiResponse.message())
            }

        } catch (e: Exception) {
            e.message?.let { onError(it) }
        }
    }

    suspend fun asLiveData(onSuccess: () -> Unit, onError: (String) -> Unit): LiveData<T> =
        MutableLiveData<T>().apply {
            fetch({
                onSuccess()
                postValue(it)
            }, onError)
        }

    abstract suspend fun fetchFromNetwork(): Response<T>
}