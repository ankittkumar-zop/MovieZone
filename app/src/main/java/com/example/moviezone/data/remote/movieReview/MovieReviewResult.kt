package com.example.moviezone.data.remote.movieReview


import com.google.gson.annotations.SerializedName

data class MovieReviewResult(

    @SerializedName("id") val id: Int?,
    @SerializedName("page") val page: Int?,
    @SerializedName("results") val results: List<MovieReviewData?>,
    @SerializedName("total_pages") val totalPages: Int?,
    @SerializedName("total_results") val totalResults: Int?
)