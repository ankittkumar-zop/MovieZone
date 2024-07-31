package com.example.moviezone.data.remote

import android.graphics.Movie
import com.example.moviezone.data.remote.movieListt.MovieListData
import com.example.moviezone.data.remote.movieListt.MovieResult
import com.example.moviezone.data.remote.movieReview.MovieReviewResult
import com.example.moviezone.data.remote.movieTrailer.MovieTrailerResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiCall {

    @GET("{category}")
    suspend fun getMovies(
        @Path("category") category: String,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
    ): Response<MovieResult>

    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") movieId: Int, @Query("api_key") apiKey: String): Response<MovieListData>

    @GET("movie/{id}/videos")
    suspend fun getMovieTrailer(@Path("id") movieId: Int,@Query("api_key") apiKey: String): Response<MovieTrailerResult>

    @GET("movie/{id}/reviews")
    suspend fun getMovieReview(@Path("id") movieId: Int, @Query("api_key") apiKey: String): Response<MovieReviewResult>
}