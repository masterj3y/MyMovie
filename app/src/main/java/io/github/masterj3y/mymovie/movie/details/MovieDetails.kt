package io.github.masterj3y.mymovie.movie.details

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "movies")
data class MovieDetails(
    @PrimaryKey
    @field:Json(name = "imdbID")
    val movieId: String,
    @field:Json(name = "Title")
    val title: String = "",
    @field:Json(name = "Poster")
    val poster: String = "",
    @field:Json(name = "Plot")
    val description: String,
    var inWatchlist: Boolean = false
)
