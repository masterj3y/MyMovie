package io.github.masterj3y.mymovie.movie.details

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
@Entity(tableName = "movies")
data class MovieDetails(
    @PrimaryKey
    @field:Json(name = "imdbID")
    var movieId: String = "",
    @field:Json(name = "Title")
    var title: String = "",
    @field:Json(name = "Poster")
    var poster: String = "",
    @field:Json(name = "Plot")
    var description: String = "",
    var isFavorite: Boolean = false
) : Parcelable
