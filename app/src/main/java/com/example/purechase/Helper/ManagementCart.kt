package com.example.purechase.Helper

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.purechase.Domain.ItemsDomain
import java.util.ArrayList

class ManagmentCart(private val context: Context) {
    private val tinyDB: TinyDB = TinyDB(context)

    fun insertFood(item: ItemsDomain) {
        val listFood = getListCart()
        var existAlready = false
        var n = 0
        for (y in listFood.indices) {
            if (listFood[y].title == item.title) {
                existAlready = true
                n = y
                break
            }
        }
        if (existAlready) {
            listFood[n].numberInCart = item.numberInCart
        } else {
            listFood.add(item)
        }
        tinyDB.putListObject("CartList", listFood)
        Log.d("ManagmentCart", "Item added to cart: ${item.title}")
        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show()
    }

    fun getListCart(): ArrayList<ItemsDomain> {
        val listFood = tinyDB.getListObject("CartList")
        Log.d("ManagmentCart", "Cart items retrieved: ${listFood.size}")
        return listFood
    }

    fun minusItem(listFood: ArrayList<ItemsDomain>, position: Int, changeNumberItemsListener: ChangeNumberItemsListener) {
        if (listFood[position].numberInCart == 1) {
            listFood.removeAt(position)
        } else {
            listFood[position].numberInCart -= 1
        }
        tinyDB.putListObject("CartList", listFood)
        changeNumberItemsListener.changed()
    }

    fun plusItem(listFood: ArrayList<ItemsDomain>, position: Int, changeNumberItemsListener: ChangeNumberItemsListener) {
        listFood[position].numberInCart += 1
        tinyDB.putListObject("CartList", listFood)
        changeNumberItemsListener.changed()
    }

    fun getTotalFee(): Double {
        var fee = 0.0
        for (item in getListCart()) {
            fee += item.price * item.numberInCart
        }
        return fee
    }

    fun clearCart() {
        tinyDB.putListObject("CartList", ArrayList<ItemsDomain>())
        Log.d("ManagmentCart", "Cart cleared")
        Toast.makeText(context, "Cart cleared", Toast.LENGTH_SHORT).show()
    }
}
