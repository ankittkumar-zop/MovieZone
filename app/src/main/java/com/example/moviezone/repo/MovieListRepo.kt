package com.example.moviezone.repo

import com.example.moviezone.data.remote.ApiCall
import javax.inject.Inject

class MovieListRepo @Inject constructor(
    private val apiCall: ApiCall
) {
    suspend fun getPopularMovies(apiKey: String, page: Int) = apiCall.getPopularMovies(apiKey, page)

    suspend fun getTopRatedMovies(apiKey: String, page: Int) = apiCall.getTopRatedMovies(apiKey, page)

}