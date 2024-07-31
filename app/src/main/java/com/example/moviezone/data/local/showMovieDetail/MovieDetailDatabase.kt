package com.example.moviezone.data.local.showMovieDetail

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviezone.data.remote.movieListt.MovieLocalData
import dagger.hilt.android.qualifiers.ApplicationContext

@Database(
    entities = [MovieLocalData::class], version = 3, exportSchema = false
)
abstract class MovieDetailDatabase : RoomDatabase() {

    abstract fun movieDetailDao(): MovieDetailDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDetailDatabase? = null

        fun getDatabase(@ApplicationContext context: Context): MovieDetailDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDetailDatabase::class.java,
                    "movie_detail_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}