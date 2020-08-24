package io.github.masterj3y.mymovie.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.masterj3y.mymovie.movie.MovieDao
import io.github.masterj3y.mymovie.movie.details.MovieDetails

@Database(entities = [MovieDetails::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}