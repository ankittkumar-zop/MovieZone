package com.example.moviezone.ui.movieDetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviezone.R
import com.example.moviezone.data.remote.movieTrailer.MovieTrailerData
import com.squareup.picasso.Picasso

class TrailerAdapter(
    private val onTrailerClick: (String) -> Unit
) : RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>() {

    private var trailers: List<MovieTrailerData?> = listOf()

    class TrailerViewHolder(view: View, private val onTrailerClick: (String) -> Unit) :
        RecyclerView.ViewHolder(view) {
        private val trailerImage: ImageView = view.findViewById(R.id.ivTrailerThumbnail)

        fun bind(trailer: MovieTrailerData) {
            loadTrailerImage(trailer.key)
            itemView.setOnClickListener {
                if (trailer.key?.isNotBlank() == true) {
                    onTrailerClick(trailer.key)
                }
            }
        }

        private fun loadTrailerImage(key: String?) {
            val imageUrl = key?.let { "https://img.youtube.com/vi/$it/0.jpg" }
            if (imageUrl != null) {
                Picasso.get().load(imageUrl).placeholder(R.drawable.movie_missing).into(trailerImage)
            } else {
                trailerImage.setImageResource(R.drawable.movie_missing)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trailer, parent, false)
        return TrailerViewHolder(view, onTrailerClick)
    }

    override fun getItemCount(): Int {
        return trailers.size
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        trailers.getOrNull(position)?.let {
            holder.bind(it)
        }
    }

    fun updateTrailerList(trailer: List<MovieTrailerData?>) {
        trailers = trailer
        notifyDataSetChanged()
    }
}