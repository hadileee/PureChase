package com.example.purechase.Activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.purechase.Domain.ItemsDomain
import com.example.purechase.R

class LikedItemsAdapter(private val likedItems: List<ItemsDomain>) : RecyclerView.Adapter<LikedItemsAdapter.LikedItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikedItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_liked, parent, false)
        return LikedItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: LikedItemViewHolder, position: Int) {
        holder.bind(likedItems[position])
    }

    override fun getItemCount(): Int {
        return likedItems.size
    }

    class LikedItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val likedItemTextView: TextView = itemView.findViewById(R.id.likeTitle)

        fun bind(item: ItemsDomain) {
            likedItemTextView.text = item.title
        }
    }
}