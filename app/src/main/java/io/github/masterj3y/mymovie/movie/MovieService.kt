package io.github.masterj3y.mymovie.movie

import io.github.masterj3y.mymovie.movie.search.SearchMovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET(".")
    suspend fun search(@Query("s") title: String): Response<SearchMovieResponse>
}