package com.example.moviezone.data.local.showMovieDetail

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviezone.data.remote.movieListt.MovieListData

@Dao
interface MovieDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(movieListData: MovieListData)

    @Query("SELECT * FROM movie_detail")
    fun getAllPost(): LiveData<List<MovieListData>>

    @Query(" UPDATE movie_detail SET isLiked = NOT isLiked WHERE id = :movieId;")
    suspend fun toggle(movieId: Int)
}