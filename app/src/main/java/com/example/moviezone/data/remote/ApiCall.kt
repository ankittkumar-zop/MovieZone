package com.example.moviezone.data.remote

import com.example.moviezone.data.Resource
import com.example.moviezone.data.remote.MovieList.MovieResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiCall {

    @GET("{category}")
    suspend fun getMovies(
        @Path("category") category: String,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<MovieResult>

}