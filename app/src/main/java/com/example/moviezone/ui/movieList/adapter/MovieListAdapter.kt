package com.example.moviezone.ui.movieList.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviezone.R
import com.example.moviezone.data.remote.MovieList.MovieListData
import com.squareup.picasso.Picasso

class MovieListAdapter(
    private val moreMovies: () -> Unit
) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {
    private var movies: List<MovieListData> = listOf()

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val itemPosterImageView: ImageView = view.findViewById(R.id.ivMovieView)
        fun setMovie(movie: MovieListData) {
            Picasso.get().load("https://image.tmdb.org/t/p/w500${movie.posterUrl}")
                .into(itemPosterImageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.setMovie(movie)
        if (position == movies.size - 3) moreMovies()
    }

    fun updateList(newMovies: List<MovieListData>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}

