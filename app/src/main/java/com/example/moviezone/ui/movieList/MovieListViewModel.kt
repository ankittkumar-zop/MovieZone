package com.example.moviezone.ui.movieList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviezone.data.Resource
import com.example.moviezone.data.remote.MovieList.MovieListData
import com.example.moviezone.repo.MovieListRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val movieRepo: MovieListRepo) : ViewModel() {

    private val _popularMovies = MutableLiveData<Resource<List<MovieListData>>>()
    val popularMovies: LiveData<Resource<List<MovieListData>>> get() = _popularMovies

    private val _topRatedMovies = MutableLiveData<Resource<List<MovieListData>>>()
    val topRatedMovies: LiveData<Resource<List<MovieListData>>> get() = _topRatedMovies

    fun fetchPopularMovies(apikey: String, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _popularMovies.postValue(Resource.Loading())
            val result = movieRepo.getPopularMovies(apikey, page)
            if (result is Resource.Success) {
                _popularMovies.postValue(Resource.Success(result.data?.results ?: emptyList()))
            }
        }
    }

    fun fetchTopRatedMovies(apikey: String, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _popularMovies.postValue(Resource.Loading())
            val result = movieRepo.getPopularMovies(apikey, page)
            if (result is Resource.Success) {
                _popularMovies.postValue(Resource.Success(result.data?.results ?: emptyList()))
            }
        }
    }
}