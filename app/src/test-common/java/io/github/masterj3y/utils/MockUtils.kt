package io.github.masterj3y.utils

import io.github.masterj3y.mymovie.movie.details.MovieDetails
import io.github.masterj3y.mymovie.movie.search.SearchMovieItem
import io.github.masterj3y.mymovie.movie.search.SearchMovieResponse

object MockUtils {

    fun mockedMovieDetails() = MovieDetails(
        movieId = "movie-1",
        title = "movie",
        description = "description",
        poster = "poster",
        inWatchlist = true
    )

    fun mockedMovieSearchItem() = SearchMovieItem(
        "movie-1",
        "Forrest Gump",
        "poster-url"
    )

    fun mockedSearchMovieResult() = SearchMovieResponse(
        result = mutableListOf(mockedMovieSearchItem()),
        error = null
    )
}