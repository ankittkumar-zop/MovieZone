package com.example.moviezone.data.remote.MovieList

import com.google.gson.annotations.SerializedName

data class MovieResult(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<MovieListData>,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)
