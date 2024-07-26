package com.example.moviezone.ui.movieList

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
class MovieListViewModel @Inject constructor(private val movieRepo : MovieListRepo): ViewModel() {

    private val _popularMovies= MutableLiveData<List<MovieListData>>()
    val popularMovies: LiveData<List<MovieListData>> get() = _popularMovies

    private val _topRatedMovies= MutableLiveData<List<MovieListData>>()
    val topRatedMovies: LiveData<List<MovieListData>> get() = _topRatedMovies

    fun fetchPopularMovies(apikey: String , page : Int ){
        viewModelScope.launch {
            _popularMovies.postValue(movieRepo.getPopularMovies(apikey, page).results)
        }
    }

    fun fetchTopRatedMovies(apikey: String , page : Int ){
        viewModelScope.launch{
            _topRatedMovies.postValue(movieRepo.getTopRatedMovies(apikey, page).results)
        }
    }
}