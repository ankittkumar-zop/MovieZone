package com.example.moviezone.ui.movieList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviezone.data.Resource
import com.example.moviezone.data.remote.movieListt.MovieListData
import com.example.moviezone.repo.MovieListRepo
import com.example.moviezone.ui.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val movieRepo: MovieListRepo) : ViewModel() {

    private val _movies = MutableLiveData<Resource<List<MovieListData?>>>()
    val movies: LiveData<Resource<List<MovieListData?>>> get() = _movies

    private var currentPage = 1
    private var totalPages = 1
    private val _selectCategory = MutableLiveData(MainActivity.POPULAR)
    val selectCategory: LiveData<String> = _selectCategory

    init {
        fetchMovies()
    }

    fun setSelectedCategory(category: String) {
        if (category != _selectCategory.value) _selectCategory.postValue(category)
    }

    fun fetchMovies(curPage: Int = 1) {
        currentPage = curPage
        if (currentPage == 1) {
            _movies.postValue(Resource.Loading())
        }
        if (currentPage > totalPages) return
        viewModelScope.launch {
            val response = movieRepo.fetchMovies(_selectCategory.value ?: "popular", currentPage)
            when (response) {
                is Resource.Success -> {
                    val movieResponse = response.data
                    movieResponse?.let {
                        val movieList = it.results ?: emptyList()
                        if (currentPage == 1) {
                            _movies.postValue(Resource.Success(movieList))
                        } else {
                            val existingMovies =
                                (_movies.value as? Resource.Success)?.data ?: emptyList()
                            _movies.postValue(Resource.Success(existingMovies + movieList))
                        }
                        totalPages = it.totalPages ?: 1
                    }
                }

                is Resource.Loading -> {

                }

                is Resource.Error -> {
                    if (currentPage == 1) {
                        _movies.value = Resource.Error("Oops!! Something went wrong.")
                    }
                }
            }
        }
    }

    fun moreMovies() {
        if (!hasMorePages()) return

        ++currentPage
        fetchMovies(currentPage)
    }

    private fun hasMorePages(): Boolean {
        return currentPage <= totalPages
    }
}