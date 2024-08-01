package com.example.moviezone.ui.movieList

import android.content.res.Configuration
import android.os.Bundle
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
import com.example.moviezone.ui.movieList.adapter.MovieListAdapter
import dagger.hilt.android.AndroidEntryPoint

const val apiKey = "78485b82b46c3312b295e2d81f160230"

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private val movieListViewModel: MovieListViewModel by viewModels()
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
        movieAdapter = MovieListAdapter(onMovieClick = { movie ->
            val bundle = Bundle().apply {
                putInt("MovieId", movie)
            }
            val movieDetailFragment = MovieDetailFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, movieDetailFragment.apply {
                    arguments = bundle
                }).addToBackStack(null).commit()
        }, moreMovies = { movieListViewModel.moreMovies() })

        recyclerView.apply {
            layoutManager = GridLayoutManager(context, getSpanCount())
            adapter = movieAdapter
        }

        movieListViewModel.movies.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    progressBar.isVisible = false
                    recyclerView.isVisible = true
                    resource.data?.let { newMovies ->
                        movieAdapter.updateList(newMovies)
                        movieAdapter.notifyDataSetChanged()
                    }
                }

                is Resource.Loading -> {
                    progressBar.isVisible = true
                    recyclerView.isVisible = false
                }

                is Resource.Error -> {
                    Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        movieListViewModel.selectCategory.observe(viewLifecycleOwner) { category ->
            if (movieListViewModel.lastCategory != category) {
                movieListViewModel.lastCategory = category
                movieListViewModel.fetchMovies()

            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val manager: GridLayoutManager = recyclerView.layoutManager as GridLayoutManager
        manager.spanCount =
            if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 2
    }

    private fun getSpanCount(): Int {
        return if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 2
    }

    fun setCategory(category: String) {
        movieListViewModel.setSelectedCategory(category)
    }
}
