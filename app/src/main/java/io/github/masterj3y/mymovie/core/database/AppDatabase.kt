package io.github.masterj3y.mymovie.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase()