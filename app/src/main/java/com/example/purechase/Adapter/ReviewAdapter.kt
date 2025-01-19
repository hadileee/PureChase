package com.example.purechase.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.example.purechase.Domain.ReviewDomain
import com.example.purechase.R
import com.example.purechase.databinding.ViewholderReviewBinding

class ReviewAdapter(private val items: ArrayList<ReviewDomain>) : RecyclerView.Adapter<ReviewAdapter.Viewholder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        context = parent.context
        val binding = ViewholderReviewBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        holder.binding.nameTxt.text = items[position].name
        holder.binding.descTxt.text = items[position].description
        holder.binding.ratingTxt.text = items[position].rating.toString()

        Glide.with(context)
            .load(items[position].picUrl)
            .transform(GranularRoundedCorners(100f, 100f, 100f, 100f))
            .into(holder.binding.pic)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class Viewholder(val binding: ViewholderReviewBinding) : RecyclerView.ViewHolder(binding.root)
}
