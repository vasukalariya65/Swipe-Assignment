package com.example.myproduct.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myproduct.R
import com.example.myproduct.databinding.FragmentAddProductBinding
import com.example.myproduct.models.AddProductModel
import com.example.myproduct.ui.ProductViewModel
import com.example.myproduct.ui.ProductViewModelFactory
import com.example.myproduct.utils.Constants.Companion.EMPTY_STRING
import org.koin.android.ext.android.get

class AddProductFragment : Fragment() {
    private lateinit var mBinding: FragmentAddProductBinding
    private lateinit var productViewModel: ProductViewModel
    private lateinit var viewModelProviderFactory: ProductViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_product, container, false)
        viewModelProviderFactory = ProductViewModelFactory(get())
        productViewModel =
            ViewModelProvider(this, viewModelProviderFactory)[ProductViewModel::class.java]

        // Adapter for Spinner drop down
        val languages = resources.getStringArray(R.array.product_type)
        val arrayAdapter = object :
            ArrayAdapter<String>(requireContext(), R.layout.product_type_dropdown, languages) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view: View = super.getView(position, convertView, parent)
                view.setPadding(5, 0, 0, 0)
                return view
            }
        }
        mBinding.sProductType.adapter = arrayAdapter

        mBinding.apply {
            viewModel = productViewModel
            lifecycleOwner = this@AddProductFragment
        }

        // handled on click for to add new product
        mBinding.btnAddProduct.setOnClickListener {
            if (validateInput())
                productViewModel.addProductItem(getProductDetails())
            else
                Toast.makeText(requireContext(), resources.getString(R.string.please_provide_a_valid_input), Toast.LENGTH_SHORT)
                    .show()
        }
        return mBinding.root
    }


    private fun validateInput(): Boolean {
        mBinding.apply {
            return productViewModel.validateInputFields(
                etProductName.text.toString(),
                sProductType.selectedItem.toString(),
                etProductPrice.text.toString(),
                etProductTax.text.toString()
            )
        }
    }

    override fun onResume() {
        super.onResume()
        productViewModel.getAddProductItemCallBack().observe(this) {
            if (it.data != null) {
                Toast.makeText(requireContext(), it.data.message, Toast.LENGTH_SHORT).show()
                parentFragmentManager.popBackStack()
            } else {
                Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getProductDetails(): AddProductModel {
        return AddProductModel(
            product_name = mBinding.etProductName.text?.toString(),
            product_type = mBinding.sProductType.selectedItem.toString(),
            price = mBinding.etProductPrice.text?.toString(),
            tax = mBinding.etProductTax.text?.toString()
        )
    }

    companion object {
        fun newInstance() = AddProductFragment()
    }
}