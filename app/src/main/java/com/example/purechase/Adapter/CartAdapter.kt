package com.example.purechase.Adapter

import com.example.purechase.Domain.ItemsDomain
import com.example.purechase.Helper.ManagmentCart
import com.example.purechase.databinding.ViewholderCartBinding
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.example.purechase.Helper.ChangeNumberItemsListener

class CartAdapter(
    private val listItemSelected: ArrayList<ItemsDomain>,
    context: Context,
    private val changeNumberItemsListener: ChangeNumberItemsListener
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private val managmentCart: ManagmentCart = ManagmentCart(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        holder.binding.titleTxt.text = listItemSelected[position].title
        holder.binding.feeEachItem.text = "${listItemSelected[position].price}dt"
        holder.binding.totalEachItem.text = "${Math.round(listItemSelected[position].numberInCart * listItemSelected[position].price)}dt"
        holder.binding.numberItemTxt.text = listItemSelected[position].numberInCart.toString()

        val requestOptions = RequestOptions().transform(CenterCrop())
        Glide.with(holder.itemView.context)
            .load(listItemSelected[position].picUrl[0])
            .apply(requestOptions)
            .into(holder.binding.pic)

        holder.binding.plusCartBtn.setOnClickListener {
            managmentCart.plusItem(listItemSelected, position, object : ChangeNumberItemsListener {
                override fun changed() {
                    notifyDataSetChanged()
                    changeNumberItemsListener.changed()
                }
            })
        }

        holder.binding.minusCartBtn.setOnClickListener {
            managmentCart.minusItem(listItemSelected, position, object : ChangeNumberItemsListener {
                override fun changed() {
                    notifyDataSetChanged()
                    changeNumberItemsListener.changed()
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return listItemSelected.size
    }

    class ViewHolder(var binding: ViewholderCartBinding) : RecyclerView.ViewHolder(binding.root)
}
