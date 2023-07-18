package com.example.myproduct.repository

import com.example.myproduct.apis.RetrofitHelper
import com.example.myproduct.models.AddProductModel
import com.example.myproduct.models.AddProductResponse
import com.example.myproduct.models.ProductResponse
import com.example.myproduct.utils.Constants
import com.example.myproduct.utils.Constants.Companion.PRODUCT_NAME
import com.example.myproduct.utils.Constants.Companion.PRODUCT_PRICE
import com.example.myproduct.utils.Constants.Companion.PRODUCT_TAX
import com.example.myproduct.utils.Constants.Companion.PRODUCT_TYPE
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface ProductRepository {
    suspend fun getAllProducts() : Response<ProductResponse>

    suspend fun addProducts(productItem: AddProductModel): Response<AddProductResponse>
}