package com.example.moviezone.data.local.showMovieDetail

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviezone.data.remote.movieListt.MovieLocalData

@Dao
interface MovieDetailDao {

    @Query("SELECT isLiked FROM movie_detail WHERE id= :movieId")
    fun getIsFav(movieId: Int): LiveData<Boolean>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(movieLocalData: MovieLocalData)

    @Query(" UPDATE movie_detail SET isLiked = NOT isLiked WHERE id = :movieId;")
    suspend fun toggle(movieId: Int)
}