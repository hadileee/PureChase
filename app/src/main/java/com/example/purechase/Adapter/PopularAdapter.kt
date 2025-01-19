package com.example.purechase.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.purechase.Activity.DetailActivity
import com.example.purechase.Domain.ItemsDomain
import com.example.purechase.Helper.LikeManager
import com.example.purechase.R
import com.example.purechase.databinding.ViewholderPoplistBinding

class PopularAdapter(private val items: ArrayList<ItemsDomain>) : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    private lateinit var context: Context
    private lateinit var likeManager: LikeManager

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        likeManager = LikeManager(context)
        val binding = ViewholderPoplistBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(var binding: ViewholderPoplistBinding) : RecyclerView.ViewHolder(binding.root) {
        private val likeIcon: ImageView = binding.heartIcon

        fun bind(item: ItemsDomain) {
            binding.title.text = item.title
            binding.reviewTxt.text = item.review.toString()
            binding.priceTxt.text = "${item.price}dt"
            binding.ratingTxt.text = "(${item.rating})"
            binding.oldPriceTxt.text = "${item.oldPrice}dt"
            binding.oldPriceTxt.paintFlags = binding.oldPriceTxt.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            binding.ratingBar.rating = item.rating.toFloat()

            val requestOptions = RequestOptions().transform(CenterCrop())
            Glide.with(context)
                .load(item.picUrl[0])
                .apply(requestOptions)
                .into(binding.pic)

// Set the initial state of the like icon
            if (likeManager.isLiked(item)) {
                likeIcon.setImageResource(R.drawable.icon_filledheart)
            } else {
                likeIcon.setImageResource(R.drawable.icon_outlineheart)
            }

            likeIcon.setOnClickListener {
                if (likeManager.isLiked(item)) {
                    likeManager.removeLike(item)
                    likeIcon.setImageResource(R.drawable.icon_outlineheart)
                } else {
                    likeManager.addLike(item)
                    likeIcon.setImageResource(R.drawable.icon_filledheart)
                }
            }

            itemView.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra("object", item)
                }
                context.startActivity(intent)
            }
        }
    }
}
