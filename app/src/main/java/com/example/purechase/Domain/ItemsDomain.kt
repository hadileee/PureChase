package com.example.purechase.Domain

import java.io.Serializable

data class ItemsDomain(
    var id: Int = 0, // Add this line
    var title: String = "",
    var description: String = "",
    var picUrl: ArrayList<String> = ArrayList(),
    var price: Double = 0.0,
    var oldPrice: Double = 0.0,
    var review: Int = 0,
    var rating: Double = 0.0,
    var numberInCart: Int = 0
) : Serializable