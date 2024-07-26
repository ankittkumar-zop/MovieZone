package com.example.moviezone.data.remote

import com.example.moviezone.data.remote.MovieList.MovieResult
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCall {

    @GET("popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String, @Query("page") page: Int
    ): MovieResult

    @GET("top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String, @Query("page") page: Int
    ): MovieResult
}