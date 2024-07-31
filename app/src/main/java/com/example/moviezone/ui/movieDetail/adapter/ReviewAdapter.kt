package com.example.moviezone.ui.movieDetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviezone.R
import com.example.moviezone.data.remote.movieReview.MovieReviewData
import com.example.moviezone.data.remote.movieReview.MovieReviewResult
import com.example.moviezone.data.remote.movieTrailer.MovieTrailerData

class ReviewAdapter: RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    private var reviews = listOf<MovieReviewData?>()

    class ReviewViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val reviewText: TextView = view.findViewById(R.id.tvReviewContent)
        fun bind ( review: MovieReviewData){
            reviewText.text = review.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        reviews.getOrNull(position)?.let {
            holder.bind(it)
        }
    }

    fun updateReviewList(newReview: List<MovieReviewData?>) {
        reviews = newReview
        notifyDataSetChanged()
    }
}