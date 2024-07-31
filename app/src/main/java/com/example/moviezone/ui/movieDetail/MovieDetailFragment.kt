package com.example.moviezone.ui.movieDetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviezone.R
import com.example.moviezone.data.remote.movieListt.MovieListData
import com.example.moviezone.data.remote.movieReview.MovieReviewData
import com.example.moviezone.data.remote.movieTrailer.MovieTrailerData
import com.example.moviezone.ui.MainActivity
import com.example.moviezone.ui.movieDetail.adapter.ReviewAdapter
import com.example.moviezone.ui.movieDetail.adapter.TrailerAdapter
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

private const val TRAILER_URL = "https://www.youtube.com/watch?v="

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private val movieDetailViewModel: MovieDetailViewModel by viewModels()
    private lateinit var trailerAdapter: TrailerAdapter
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var bgMoviePoster: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var movieYear: TextView
    private lateinit var movieOverview: TextView
    private lateinit var likeButton: ImageButton
    private lateinit var trailerRecyclerView: RecyclerView
    private lateinit var reviewRecyclerView: RecyclerView
    private lateinit var backButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews(view)
        setupAdapters()
        setupRecyclerViews()
        setupObservers()
        setupClickListeners()

        arguments?.getInt("MovieId")?.let { movieId ->
            movieDetailViewModel.loadData(movieId)
            movieDetailViewModel.getIsFavourites(movieId)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.hideToolbar()
    }

    override fun onPause() {
        super.onPause()
        (activity as? MainActivity)?.showToolbar()
    }

    private fun initializeViews(view: View) {
        bgMoviePoster = view.findViewById(R.id.bg_movie_poster)
        movieTitle = view.findViewById(R.id.movie_title)
        movieYear = view.findViewById(R.id.movie_year)
        movieOverview = view.findViewById(R.id.movie_description)
        trailerRecyclerView = view.findViewById(R.id.trailer_recyclerview)
        reviewRecyclerView = view.findViewById(R.id.review_recyclerview)
        likeButton = view.findViewById(R.id.like_button)
        backButton = view.findViewById(R.id.back_button)
    }

    private fun setupAdapters() {
        trailerAdapter = TrailerAdapter { trailerKey ->
            openTrailer(trailerKey)
        }
        reviewAdapter = ReviewAdapter()
    }

    private fun setupRecyclerViews() {
        trailerRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = trailerAdapter
        }

        reviewRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = reviewAdapter
        }
    }

    private fun setupObservers() {
        movieDetailViewModel.movieDetails.observe(viewLifecycleOwner, Observer { movie ->
            movie?.let { updateDetail(it) }
        })

        movieDetailViewModel.trailers.observe(viewLifecycleOwner) { trailers ->
            trailers?.let { updateTrailer(it) }
        }

        movieDetailViewModel.reviews.observe(viewLifecycleOwner) { reviews ->
            reviews?.let { updateReview(it) }
        }

        movieDetailViewModel.getIsFavourites(arguments?.getInt("MovieId") ?: 0)
            .observe(viewLifecycleOwner) { isFav ->
                isFav?.let { updateLikeButton(it) }
            }
    }

    private fun setupClickListeners() {
        likeButton.setOnClickListener {
            arguments?.getInt("MovieId")?.let { movieId ->
                movieDetailViewModel.toggleLike(movieId)
            }
        }

        backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun openTrailer(trailerKey: String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(TRAILER_URL + trailerKey)))
        } catch (e: Exception) {
            Toast.makeText(context, "Failed to load trailer", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateLikeButton(isLiked: Boolean) {
        likeButton.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                if (isLiked) R.drawable.heart_filled else R.drawable.heart_border
            )
        )
    }

    private fun updateReview(reviews: List<MovieReviewData?>) {
        reviewAdapter.updateReviewList(reviews)
    }

    private fun updateTrailer(trailers: List<MovieTrailerData?>) {
        trailerAdapter.updateTrailerList(trailers)
    }

    private fun updateDetail(detail: MovieListData) {
        Picasso.get().load("https://image.tmdb.org/t/p/w500${detail.backgroundImage}").placeholder(R.drawable.poster_missing)
            .into(bgMoviePoster)
        movieTitle.text = detail.movieTitle
        movieYear.text = detail.year
        movieOverview.text = detail.overview
    }
}
