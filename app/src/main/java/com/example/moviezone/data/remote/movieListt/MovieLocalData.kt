package com.example.moviezone.data.remote.movieListt

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie_detail")
data class MovieLocalData(
    @PrimaryKey @SerializedName("id") var id: Int, var isLiked: Boolean = false
)
