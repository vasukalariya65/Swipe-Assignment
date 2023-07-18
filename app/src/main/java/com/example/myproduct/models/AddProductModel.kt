package com.example.myproduct.models

import java.io.File

data class AddProductModel(
    val product_name: String?,
    val product_type: String?,
    val price: String?,
    val tax: String?,
    val files: File? = null,
)