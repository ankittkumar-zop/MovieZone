package com.example.moviezone.data.remote.movieListt

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class MovieListData(
    @PrimaryKey @SerializedName("id") var id: Int,
    @SerializedName("poster_path") var posterUrl: String?,
    @SerializedName("overview") var overview: String?,
    @SerializedName("release_date") var year: String?,
    @SerializedName("backdrop_path") var backgroundImage: String?,
    @SerializedName("title") var movieTitle: String?,
)
