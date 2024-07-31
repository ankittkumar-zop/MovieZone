package com.example.moviezone.repo

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.moviezone.data.Resource
import com.example.moviezone.data.local.showMovieDetail.MovieDetailDao
import com.example.moviezone.data.remote.ApiCall
import com.example.moviezone.data.remote.movieListt.MovieListData
import com.example.moviezone.data.remote.movieReview.MovieReviewData
import com.example.moviezone.data.remote.movieTrailer.MovieTrailerData
import com.example.moviezone.data.remote.movieTrailer.MovieTrailerResult
import com.example.moviezone.ui.movieList.apiKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class MovieDetailRepo @Inject constructor(private val apiCall: ApiCall, private val movieDetailDao: MovieDetailDao) {

    fun observerDb(): LiveData<List<MovieListData>> = movieDetailDao.getAllPost()

    suspend fun getDetail(movieId: Int): Resource<MovieListData?>{
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall.getMovieDetails(movieId = movieId , apiKey = apiKey)
                if (response.isSuccessful) {
                    Resource.Success(response.body())

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

    suspend fun  getTrailer(movieId : Int ) : Resource<List<MovieTrailerData?>>{
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall.getMovieTrailer(movieId = movieId , apiKey = apiKey)
                if (response.isSuccessful) {
                    Log.d("debug" , "successfull repo in api")

                    Resource.Success(response.body()?.results ?: emptyList())
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

    suspend fun getReview(movieId:Int ): Resource<List<MovieReviewData?>>{
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall.getMovieReview(movieId = movieId , apiKey = apiKey)
                if (response.isSuccessful) {
                    Log.d("debug" , "successfull repo in api")

                    Resource.Success(response.body()?.results ?: emptyList())
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

    suspend fun fetchData(movieId:Int ) {
        withContext(Dispatchers.IO) {
            val response = apiCall.getMovieDetails(movieId = movieId, apiKey = apiKey)
            if (response.isSuccessful) {
                response.body()?.let { movie ->
//                    movieDetailDao.insertData(movie)
                }
            }
        }
    }

    suspend fun toggle(movieId: Int) {
        withContext(Dispatchers.IO) {
            movieDetailDao.toggle(movieId = movieId )
        }
    }
}