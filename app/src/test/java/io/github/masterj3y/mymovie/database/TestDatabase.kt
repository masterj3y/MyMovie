package io.github.masterj3y.mymovie.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import io.github.masterj3y.mymovie.core.database.AppDatabase
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
abstract class TestDatabase {

    lateinit var db: AppDatabase

    @Before
    fun createDB() {
        db = Room.inMemoryDatabaseBuilder(getApplicationContext(), AppDatabase::class.java)
            .allowMainThreadQueries().build()
    }

    @After
    fun closeDB() {
        db.close()
    }
}