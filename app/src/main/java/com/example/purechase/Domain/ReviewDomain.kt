package com.example.purechase.Domain

data class ReviewDomain(
    var name: String = "",
    var description: String = "",
    var picUrl: String = "",
    var rating: Double = 0.0,
    var itemId: Int = 0
)
