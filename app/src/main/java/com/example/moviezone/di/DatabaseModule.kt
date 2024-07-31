package com.example.moviezone.di

import android.content.Context
import com.example.moviezone.data.local.showMovieDetail.MovieDetailDao
import com.example.moviezone.data.local.showMovieDetail.MovieDetailDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun movieDetailDatabase(@ApplicationContext context: Context): MovieDetailDatabase {
        return MovieDetailDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideMovieDetailDao(database: MovieDetailDatabase): MovieDetailDao {
        return database.movieDetailDao()
    }
}