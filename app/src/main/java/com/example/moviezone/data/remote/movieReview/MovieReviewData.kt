package com.example.moviezone.data.remote.movieReview

import com.google.gson.annotations.SerializedName

data class MovieReviewData(

    @SerializedName("author") val author: String?,
    @SerializedName("content") val content: String?,
    @SerializedName("id") val id: String?,
)