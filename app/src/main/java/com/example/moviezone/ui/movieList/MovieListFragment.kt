package com.example.moviezone.ui.movieList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviezone.R
import com.example.moviezone.data.Resource
import com.example.moviezone.ui.movieDetail.MovieDetailFragment
import com.example.moviezone.ui.movieDetail.MovieDetailViewModel
import com.example.moviezone.ui.movieList.adapter.MovieListAdapter
import dagger.hilt.android.AndroidEntryPoint

const val apiKey = "78485b82b46c3312b295e2d81f160230"

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private val movieListViewModel: MovieListViewModel by viewModels()
    private val movieDetailViewModel: MovieDetailViewModel by viewModels()
    private lateinit var movieAdapter: MovieListAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.show_movie_list_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)

        recyclerView = view.findViewById(R.id.recyclerViewLoadMovie)
        movieAdapter = MovieListAdapter(
            onMovieClick = { movie ->
                val bundle = Bundle().apply {
                    putInt("MovieId", movie)
                }
                val movieDetailFragment = MovieDetailFragment()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container,movieDetailFragment.apply {
                        arguments = bundle
                    }).addToBackStack(null)
                    .commit()
            }, moreMovies = { movieListViewModel.moreMovies() }
        )

        recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = movieAdapter
        }

        movieListViewModel.movies.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    progressBar.isVisible = false
                    recyclerView.isVisible = true
                    Log.d("kkk", "success api")
                    resource.data?.let { newMovies ->
                        movieAdapter.updateList(newMovies)
                        movieAdapter.notifyDataSetChanged()
                    }
                }

                is Resource.Loading -> {
                    progressBar.isVisible = true
                    recyclerView.isVisible = false
                    Log.d("kkk", "loading api")

                }

                is Resource.Error -> {
                    Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        movieListViewModel.selectCategory.observe(viewLifecycleOwner) { category ->
            movieListViewModel.fetchMovies()
        }
    }

    fun setCategory(category: String) {
        movieListViewModel.setSelectedCategory(category)
    }
}
