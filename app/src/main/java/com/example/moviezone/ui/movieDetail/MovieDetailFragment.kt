package com.example.moviezone.ui.movieDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviezone.R
import com.example.moviezone.data.remote.movieListt.MovieListData
import com.example.moviezone.data.remote.movieReview.MovieReviewData
import com.example.moviezone.data.remote.movieTrailer.MovieTrailerData
import com.example.moviezone.ui.movieDetail.adapter.ReviewAdapter
import com.example.moviezone.ui.movieDetail.adapter.TrailerAdapter
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

const val Trailer_Url = "https://www.youtube.com/watch?v="

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private val movieDetailViewModel: MovieDetailViewModel by viewModels()
    private lateinit var trailerAdapter: TrailerAdapter
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var bgMoviePoster: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var movieYear: TextView
    private lateinit var movieOverview: TextView
    private lateinit var trailerRecyclerView: RecyclerView
    private lateinit var reviewRecyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = arguments?.getInt("MovieId")

        bgMoviePoster = view.findViewById(R.id.bg_movie_poster)
        movieTitle = view.findViewById(R.id.movie_title)
        movieYear = view.findViewById(R.id.movie_year)
        movieOverview = view.findViewById(R.id.movie_description)
        trailerRecyclerView = view.findViewById(R.id.trailer_recyclerview)
        reviewRecyclerView = view.findViewById(R.id.review_recyclerview)

        trailerAdapter = TrailerAdapter { trailerKey ->
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Trailer_Url + trailerKey)))
            } catch (e: Exception) {
                Toast.makeText(context, " failed to load trailer ", Toast.LENGTH_SHORT).show()
            }
        }

        trailerRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = trailerAdapter
        }

        reviewAdapter = ReviewAdapter()
        reviewRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = reviewAdapter
        }

        movieId?.let { id ->
            movieDetailViewModel.loadData(id)
        }


        movieDetailViewModel.movieDetails.observe(viewLifecycleOwner, Observer { detail ->
            if (detail != null) {
                updateDetail(detail)
            }
        })

        movieDetailViewModel.trailers.observe(viewLifecycleOwner) { trailer ->
            trailer?.let {
                updateTrailer(trailer)
            }
        }

        movieDetailViewModel.reviews.observe(viewLifecycleOwner) { review ->
            review?.let {
                updateReview(review)
            }
        }
    }

    private fun updateReview(review: List<MovieReviewData?>) {
        reviewAdapter.updateReviewList(review)
    }

    private fun updateTrailer(trailer: List<MovieTrailerData?>) {
        trailerAdapter.updateTrailerList(trailer)
    }

    private fun updateDetail(detail: MovieListData) {
        Picasso.get().load("https://image.tmdb.org/t/p/w500${detail.backgroundImage}")
            .into(bgMoviePoster)
        movieTitle.text = detail.movieTitle
        movieYear.text = detail.year
        movieOverview.text = detail.overview

    }

}
