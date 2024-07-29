package com.example.moviezone.data.remote.MovieList

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class MovieListData(
    @PrimaryKey @SerializedName("id")
    var id: Int,
    @SerializedName("poster_path")
    var posterUrl: String?,
    var isLiked: Boolean = false
)
