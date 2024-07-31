package com.example.moviezone.data.remote.movieTrailer


import com.google.gson.annotations.SerializedName

data class MovieTrailerData(

    @SerializedName("id") val id: String?,
    @SerializedName("key") val key: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("official") val official: Boolean?,
    @SerializedName("site") val site: String?,
)