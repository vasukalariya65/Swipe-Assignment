package com.example.myproduct.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myproduct.R
import com.example.myproduct.databinding.ProductItemBinding
import com.example.myproduct.models.ProductResponseItem

class ProductAdapter(private val productList: MutableList<ProductResponseItem> = mutableListOf()): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    inner class ProductViewHolder(val binding: ProductItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindView(productItem: ProductResponseItem) {
            Glide.with(binding.root).load(productItem.image).placeholder(R.drawable.ic_default).into(binding.ivProductImage)
            binding.tvProductName.text ="${productItem.product_name}"
            binding.tvProductType.text = "${productItem.product_type}"
            binding.tvProductPrice.text = "Rs ${productItem.price.toString()}"
            binding.tvProductTax.text = "Tax : ${productItem.tax.toString()}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val viewBinding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindView(productList[position])
    }

    fun updateList(list: ArrayList<ProductResponseItem>) {
        productList.clear()
        productList.addAll(list)
        notifyDataSetChanged()
    }
}