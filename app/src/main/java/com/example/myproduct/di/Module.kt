package com.example.myproduct.di

import com.example.myproduct.apis.RetrofitHelper
import com.example.myproduct.repository.ProductRepository
import com.example.myproduct.repository.ProductRepositoryImpl
import com.example.myproduct.ui.ProductViewModel
import com.example.myproduct.ui.ProductViewModelFactory
import org.koin.dsl.module
import kotlin.math.sin

val appModule = module {

    single { RetrofitHelper() }

    single<ProductRepository> { ProductRepositoryImpl(get()) }

    single { ProductViewModelFactory(get()) }
}