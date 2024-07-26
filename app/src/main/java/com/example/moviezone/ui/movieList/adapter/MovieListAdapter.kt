package com.example.moviezone.ui.movieList.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviezone.R
import com.example.moviezone.data.remote.MovieList.MovieListData

class MovieListAdapter(
    private var movies: List<MovieListData>,
) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemPosterImageView: ImageView = view.findViewById(R.id.ivMovieView)
        fun bind(movie : MovieListData){
            Glide.with(itemView.context).load("https://image.tmdb.org/t/p/w500${movie.posterUrl}").into(itemPosterImageView)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    fun updateData(newMovies: List<MovieListData>) {
        movies = newMovies
        notifyDataSetChanged()
    }
}

