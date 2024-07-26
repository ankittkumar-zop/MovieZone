package com.example.moviezone.ui.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviezone.R
import com.example.moviezone.ui.movieList.adapter.MovieListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private val movieListViewModel: MovieListViewModel by viewModels()
    private lateinit var popularAdapter: MovieListAdapter
    private lateinit var topRatedAdapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.show_movie_list_recycler_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewLoadMovie)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val apiKey = "78485b82b46c3312b295e2d81f160230"
        val page =1

        movieListViewModel.fetchPopularMovies(apiKey, page)
        movieListViewModel.fetchTopRatedMovies(apiKey, page)

        movieListViewModel.popularMovies.observe(viewLifecycleOwner){ movies->
            popularAdapter = MovieListAdapter(movies)
            recyclerView.adapter = popularAdapter
        }

        movieListViewModel.topRatedMovies.observe(viewLifecycleOwner){ movies->
            topRatedAdapter = MovieListAdapter(movies)
            recyclerView.adapter = topRatedAdapter
        }

    }


}