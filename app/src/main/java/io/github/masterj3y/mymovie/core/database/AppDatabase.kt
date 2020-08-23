package io.github.masterj3y.mymovie.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.masterj3y.mymovie.movie.search.SearchMovieItem

@Database(entities = [SearchMovieItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase()