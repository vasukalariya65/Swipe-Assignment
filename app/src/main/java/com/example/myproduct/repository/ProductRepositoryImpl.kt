package com.example.myproduct.repository

import com.example.myproduct.apis.RetrofitHelper
import com.example.myproduct.models.AddProductModel
import com.example.myproduct.models.AddProductResponse
import com.example.myproduct.models.ProductResponse
import com.example.myproduct.utils.Constants.Companion.PRODUCT_NAME
import com.example.myproduct.utils.Constants.Companion.PRODUCT_PRICE
import com.example.myproduct.utils.Constants.Companion.PRODUCT_TAX
import com.example.myproduct.utils.Constants.Companion.PRODUCT_TYPE
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class ProductRepositoryImpl(private val retrofitHelper: RetrofitHelper): ProductRepository {
    // Function to get all product details
    override suspend fun getAllProducts(): Response<ProductResponse> {
        return retrofitHelper.api.getAllProducts()
    }

    // Function to add new product detail.
    override suspend fun addProducts(productItem: AddProductModel): Response<AddProductResponse> {
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(PRODUCT_NAME, productItem.product_name.toString())
            .addFormDataPart(PRODUCT_TYPE, productItem.product_type.toString())
            .addFormDataPart(PRODUCT_PRICE, productItem.price.toString())
            .addFormDataPart(PRODUCT_TAX, productItem.tax.toString())
            .build();

        return retrofitHelper.api.addProducts(requestBody)
    }

}