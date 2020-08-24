package io.github.masterj3y.mymovie.movie.details

import android.os.Parcelable
import io.github.masterj3y.mymovie.movie.search.SearchMovieItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDetailsParam(
    val id: String,
    val title: String,
    val poster: String
) : Parcelable {

    companion object {

        fun from(movieDetails: MovieDetails) =
            MovieDetailsParam(movieDetails.movieId, movieDetails.title, movieDetails.poster)

        fun from(searchMovieItem: SearchMovieItem) =
            MovieDetailsParam(searchMovieItem.id, searchMovieItem.title, searchMovieItem.poster)
    }
}