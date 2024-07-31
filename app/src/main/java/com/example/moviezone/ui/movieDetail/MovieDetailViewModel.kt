package com.example.moviezone.ui.movieDetail

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviezone.data.Resource
import com.example.moviezone.data.remote.movieListt.MovieListData
import com.example.moviezone.data.remote.movieReview.MovieReviewData
import com.example.moviezone.data.remote.movieReview.MovieReviewResult
import com.example.moviezone.data.remote.movieTrailer.MovieTrailerData
import com.example.moviezone.data.remote.movieTrailer.MovieTrailerResult
import com.example.moviezone.repo.MovieDetailRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val movieDetailRepo: MovieDetailRepo) :
    ViewModel() {

    private val _movieDetails = MutableLiveData<MovieListData?>()
    val movieDetails: LiveData<MovieListData?> get() = _movieDetails

    private val _trailers = MutableLiveData<List<MovieTrailerData?>>()
    val trailers: MutableLiveData<List<MovieTrailerData?>> get() = _trailers

    private val _reviews = MutableLiveData<List<MovieReviewData?>>()
    val reviews: MutableLiveData<List<MovieReviewData?>> get() = _reviews

    private suspend fun fetchDetail(movieId: Int) {
        viewModelScope.launch {
            val response = movieDetailRepo.getDetail(movieId)
            when(response){
                is Resource.Error -> {
                    Log.d("debug" , "error in api")
                }
                is Resource.Loading -> {
                    Log.d("debug" , "loading in api")

                }
                is Resource.Success -> {
                    Log.d("debug" , "success in api")

                    _movieDetails.postValue(response.data)
                }
            }
        }
    }

    private suspend fun fetchTrailer(movieId: Int) {
        viewModelScope.launch {

            when(val response = movieDetailRepo.getTrailer(movieId)){
                is Resource.Error -> {
                    Log.d("debug" , "error in api")
                }
                is Resource.Loading -> {
                    Log.d("debug" , "loading in api")

                }
                is Resource.Success -> {
                    Log.d("debug" , "success in api")

                    _trailers.postValue(response.data ?: emptyList<MovieTrailerData>())
                }
            }
        }
    }

    private suspend fun fetchReviews(movieId: Int) {
        viewModelScope.launch {
            when(val response = movieDetailRepo.getReview(movieId)){
                is Resource.Error -> {
                    Log.d("debug" , "error in api")
                }
                is Resource.Loading -> {
                    Log.d("debug" , "loading in api")

                }
                is Resource.Success -> {
                    Log.d("debug" , "success in api")

                    _reviews.postValue(response.data ?: emptyList<MovieReviewData>())
                }
            }
        }
    }

    fun loadData(movieId: Int) = viewModelScope.launch {
        withContext(Dispatchers.IO){
            async { fetchDetail(movieId) }
            async { fetchTrailer(movieId) }
            async { fetchReviews(movieId) }
        }
    }
}
