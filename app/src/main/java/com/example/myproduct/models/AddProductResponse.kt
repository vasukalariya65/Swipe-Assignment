package com.example.myproduct.models

data class AddProductResponse(
    val message: String,
    val product_details: AddProductModel,
    val product_id: Int,
    val success: Boolean
)