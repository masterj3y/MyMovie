package io.github.masterj3y.mymovie.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.github.masterj3y.mymovie.movie.details.MovieDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(vararg movieDetails: MovieDetails)

    @Query("SELECT * FROM movies WHERE movieId = :movieId")
    fun findMovieById(movieId: String): Flow<MovieDetails?>

    @Query("UPDATE movies SET inWatchlist = 1 WHERE movieId = :movieId")
    fun addToWatchList(movieId: String)

    @Query("UPDATE movies SET inWatchlist = 0 WHERE movieId = :movieId")
    fun removeFromWatchlist(movieId: String)

    @Query("SELECT * FROM movies WHERE inWatchlist = 1")
    fun findWatchlist(): Flow<List<MovieDetails>>

    @Query("UPDATE movies SET watchStatus = :status WHERE movieId = :movieId")
    fun changeMovieWatchStatus(movieId: String, status: String)
}