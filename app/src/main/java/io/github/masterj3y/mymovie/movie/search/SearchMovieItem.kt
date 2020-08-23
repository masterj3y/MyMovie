package io.github.masterj3y.mymovie.movie.search

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.github.masterj3y.mymovie.core.platform.BaseEntity

@Entity(tableName = "search_history")
@JsonClass(generateAdapter = true)
data class SearchMovieItem(
    @PrimaryKey
    @field:Json(name = "imdbID")
    override val id: String,
    @field:Json(name = "Title")
    val title: String,
    @field:Json(name = "Poster")
    val poster: String
) : BaseEntity<String>
