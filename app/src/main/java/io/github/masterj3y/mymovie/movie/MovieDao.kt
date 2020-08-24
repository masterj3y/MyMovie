package io.github.masterj3y.mymovie.movie

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.masterj3y.mymovie.movie.details.MovieDetails

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(vararg movieDetails: MovieDetails)

    @Query("SELECT * FROM movies WHERE movieId = :movieId")
    fun findMovieById(movieId: String): LiveData<MovieDetails>
}