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
): RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>() {

    private var trailers: List<MovieTrailerData?> = listOf()

    class TrailerViewHolder(view: View, private val onTrailerClick: (String) -> Unit): RecyclerView.ViewHolder(view) {
        private val trailerImage: ImageView = view.findViewById(R.id.ivTrailerThumbnail)

        fun bind(trailer: MovieTrailerData){
            Picasso.get().load("https://image.tmdb.org/t/p/w500/${trailer.key}.jpg").into(trailerImage)
            
            itemView.setOnClickListener{
                if(trailer.key?.isNotBlank() == true){
                    onTrailerClick(trailer.key)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trailer, parent , false)
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