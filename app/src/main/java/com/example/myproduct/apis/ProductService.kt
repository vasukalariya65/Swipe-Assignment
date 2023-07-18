package com.example.myproduct.apis

import com.example.myproduct.models.AddProductModel
import com.example.myproduct.models.AddProductResponse
import com.example.myproduct.models.ProductResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ProductService {
    @GET("/api/public/get")
    suspend fun getAllProducts(): Response<ProductResponse>

    @POST("/api/public/add")
    suspend fun addProducts(
        @Body requestBody: RequestBody
    ): Response<AddProductResponse>

}