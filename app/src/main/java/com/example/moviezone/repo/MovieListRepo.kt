package com.example.moviezone.repo

import com.example.moviezone.data.Resource
import com.example.moviezone.data.remote.ApiCall
import com.example.moviezone.data.remote.MovieList.MovieResult
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class MovieListRepo @Inject constructor(
    private val apiCall: ApiCall
) {
    suspend fun getPopularMovies(apiKey: String, page: Int): Resource<MovieResult> {
        return try {
            val response = apiCall.getPopularMovies(apiKey, page)
            Resource.Success(response)
        } catch (e: IOException) {
            Resource.Error("Network error: ${e.message}")
        } catch (e: HttpException) {
            Resource.Error("HTTP error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Unknown error: ${e.message}")
        }
    }

    suspend fun getTopRatedMovies(apiKey: String, page: Int): Resource<MovieResult> {
        return try {
            val response = apiCall.getTopRatedMovies(apiKey, page)
            Resource.Success(response)
        } catch (e: IOException) {
            Resource.Error("Network error: ${e.message}")
        } catch (e: HttpException) {
            Resource.Error("HTTP error: ${e.message}")
        } catch (e: Exception) {
            Resource.Error("Unknown error: ${e.message}")
        }
    }
}