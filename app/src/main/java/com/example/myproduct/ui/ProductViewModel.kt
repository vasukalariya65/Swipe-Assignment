package com.example.myproduct.ui

import android.util.Log
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproduct.R
import com.example.myproduct.models.AddProductModel
import com.example.myproduct.models.AddProductResponse
import com.example.myproduct.models.ProductResponse
import com.example.myproduct.models.ProductResponseItem
import com.example.myproduct.repository.ProductRepository
import com.example.myproduct.repository.ProductRepositoryImpl
import com.example.myproduct.utils.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.HashSet
import java.util.Locale
import java.util.Timer
import kotlin.concurrent.schedule

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val productList = MutableLiveData<Resources<ProductResponse>>()
    fun getProductList(): LiveData<Resources<ProductResponse>> = productList

    private val addProductItemCallBack = MutableLiveData<Resources<AddProductResponse>>()
    fun getAddProductItemCallBack(): LiveData<Resources<AddProductResponse>> =
        addProductItemCallBack

    private val progressBarVisibility: MutableLiveData<Boolean> = MutableLiveData(false)
    fun getProgressBarVisibility(): LiveData<Boolean> = progressBarVisibility

    private val displayProductVisibility: MutableLiveData<Boolean> = MutableLiveData(true)
    fun getDisplayProductVisibility(): LiveData<Boolean> = displayProductVisibility

    private val productTypeFilterList: MutableLiveData<ArrayList<String>> = MutableLiveData()
    fun getProductTypeFilterList(): LiveData<ArrayList<String>> = productTypeFilterList

    private val userFilterList: MutableLiveData<ArrayList<ProductResponseItem>> = MutableLiveData()
    fun getUserFilterList(): LiveData<ArrayList<ProductResponseItem>> = userFilterList

    fun getAllProductList() = viewModelScope.launch(Dispatchers.IO) {
        progressBarVisibility.postValue(true)
        val response = productRepository.getAllProducts()
        productList.postValue(handleProductResponse(response))
    }

    fun addProductItem(productItem: AddProductModel) = viewModelScope.launch(Dispatchers.IO) {
        progressBarVisibility.postValue(true)
        val response = productRepository.addProducts(productItem)
        addProductItemCallBack.postValue(handleAddProductResponse(response))
    }

    private fun handleProductResponse(response: Response<ProductResponse>): Resources<ProductResponse> {
        progressBarVisibility.postValue(false)
        if (response.isSuccessful) {
            displayProductVisibility.postValue(true)
            response.body()?.let {
                return Resources.Success(it)
            }
        }
        displayProductVisibility.postValue(false)
        return Resources.Error(null, response.message())
    }

    private fun handleAddProductResponse(response: Response<AddProductResponse>): Resources<AddProductResponse> {
        progressBarVisibility.postValue(false)
        if (response.isSuccessful) {
            displayProductVisibility.postValue(true)
            response.body()?.let {
                return Resources.Success(it)
            }
        }
        return Resources.Error(null, response.body()?.message)
    }

    fun updateProductFilterList() {
        if (productList.value?.data != null) {
            val currentSet = HashSet<String>()
            currentSet.add(R.string.all_products.toString())
            for (i in productList.value?.data!!) {
                currentSet.add(i.product_type)
            }
            productTypeFilterList.postValue(ArrayList(currentSet))
        }
    }

    fun handleFilter(productType: String) {
        val filteredList = java.util.ArrayList<ProductResponseItem>()
        if (productType.equals(R.string.all_products.toString()
                , true))
            userFilterList.postValue(productList.value?.data as ArrayList<ProductResponseItem>)
        else {
            for (i in productList.value?.data as ArrayList<ProductResponseItem>) {
                if (i.product_type.equals(productType, ignoreCase = true)) {
                    filteredList.add(i)
                }
            }
            userFilterList.postValue(filteredList)
        }
    }

    fun validateInputFields(name: String?, type: String?, price: String?, tax: String?): Boolean {
        if (name?.isEmpty() == true)
            return false
        if (type?.isEmpty() == true)
            return false
        if (price?.isEmpty() == true)
            return false
        if (tax?.isEmpty() == true)
            return false
        return true
    }
}