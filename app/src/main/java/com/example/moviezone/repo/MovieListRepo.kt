package com.example.moviezone.repo

import com.example.moviezone.data.Resource
import com.example.moviezone.data.remote.ApiCall
import com.example.moviezone.data.remote.MovieList.MovieResult
import com.example.moviezone.ui.movieList.apiKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import javax.inject.Inject

class MovieListRepo @Inject constructor(private val apiCall: ApiCall) {
    suspend fun fetchMovies(category: String, page: Int): Resource<MovieResult> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall.getMovies(category = category, apiKey = apiKey, page = page)
                if (response.isSuccessful) {
                    Resource.Success(response.body()!!)
                } else {
                    Resource.Error("API Error: ${response.code()} ${response.message()}")
                }
            } catch (e: IOException) {
                Resource.Error("IO error: ${e.message}")
            } catch (e: Exception) {
                Resource.Error("Failed to fetch data: ${e.message}")
            }
        }
    }
}
