package com.example.purechase.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.purechase.Domain.CategoryDomain
import com.example.purechase.databinding.ViewholderCategoryBinding

class CategoryAdapter(private val items: List<CategoryDomain>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val binding = ViewholderCategoryBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding, context)
    }

    override fun onBindViewHolder(@NonNull holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.binding.title.text = items[position].title
        Glide.with(holder.context)
            .load(items[position].picUrl)
            .into(holder.binding.pic)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(val binding: ViewholderCategoryBinding, val context: Context) : RecyclerView.ViewHolder(binding.root)
}