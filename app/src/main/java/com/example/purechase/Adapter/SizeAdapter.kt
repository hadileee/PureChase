package com.example.purechase.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.purechase.R
import com.example.purechase.databinding.ViewholderSizeBinding

class SizeAdapter(
    private val items: ArrayList<String>,
    private var context: Context
) : RecyclerView.Adapter<SizeAdapter.ViewHolder>() {

    private var selectedPosition: Int = -1
    private var lastSelectedPosition: Int = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderSizeBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.sizeTxt.text = items[position]
        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition
            selectedPosition = holder.adapterPosition
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
        }
        if (selectedPosition == holder.adapterPosition) {
            holder.binding.sizeLayout.setBackgroundResource(R.drawable.size_selected)
            holder.binding.sizeTxt.setTextColor(context.resources.getColor(R.color.darkPink))
        } else {
            holder.binding.sizeLayout.setBackgroundResource(R.drawable.size_unselected)
            holder.binding.sizeTxt.setTextColor(context.resources.getColor(R.color.black))
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(var binding: ViewholderSizeBinding) : RecyclerView.ViewHolder(binding.root)
}
