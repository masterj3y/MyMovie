package io.github.masterj3y.mymovie.movie.search

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchMovieResponse(
    @field:Json(name = "Search")
    val result: MutableList<SearchMovieItem>? = null,
    @field:Json(name = "Error")
    val error: String? = null
)
