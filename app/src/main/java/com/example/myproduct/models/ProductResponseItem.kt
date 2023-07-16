package com.example.myproduct.models

data class ProductResponseItem(
    val image: String,
    val price: Double,
    val product_name: String,
    val product_type: String,
    val tax: Double
)