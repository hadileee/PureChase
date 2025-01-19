package com.example.purechase.Helper

import android.content.Context
import com.example.purechase.Domain.ItemsDomain

class LikeManager(private val context: Context) {
    private val tinyDB: TinyDB = TinyDB(context)

    fun addLike(item: ItemsDomain) {
        val likedItems = getLikedItems()
        likedItems.add(item)
        tinyDB.putListObject("LikedItems", likedItems)
    }

    fun removeLike(item: ItemsDomain) {
        val likedItems = getLikedItems()
        likedItems.remove(item)
        tinyDB.putListObject("LikedItems", likedItems)
    }

    fun isLiked(item: ItemsDomain): Boolean {
        return getLikedItems().contains(item)
    }

    fun getLikedItems(): ArrayList<ItemsDomain> {
        return tinyDB.getListObject("LikedItems")
    }
}
