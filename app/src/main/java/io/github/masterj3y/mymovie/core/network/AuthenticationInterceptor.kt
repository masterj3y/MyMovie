package io.github.masterj3y.mymovie.core.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthenticationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()
        val originalHttpUrl = original.url
        val url = originalHttpUrl.newBuilder().addQueryParameter("apikey", API_KEY).build()
        val requestBuilder: Request.Builder = original.newBuilder().url(url)
        return chain.proceed(requestBuilder.build())
    }

    companion object {
        private const val API_KEY = "ba75ac7f"
    }
}