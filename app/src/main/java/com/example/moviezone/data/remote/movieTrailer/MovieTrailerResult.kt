package com.example.moviezone.data.remote.movieTrailer

import com.google.gson.annotations.SerializedName

data class MovieTrailerResult(

    @SerializedName("id") val id: Int?,
    @SerializedName("results") val results: List<MovieTrailerData?>
)