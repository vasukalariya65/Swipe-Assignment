package com.example.myproduct.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myproduct.R
import com.example.myproduct.adapter.ProductAdapter
import com.example.myproduct.databinding.FragmentDisplayProductBinding
import com.example.myproduct.models.ProductResponseItem
import com.example.myproduct.repository.ProductRepository
import com.example.myproduct.ui.ProductViewModel
import com.example.myproduct.ui.ProductViewModelFactory
import org.koin.android.ext.android.get
import java.util.Locale


class DisplayProductFragment : Fragment() {

    private lateinit var mBinding: FragmentDisplayProductBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productViewModel: ProductViewModel
    private lateinit var viewModelProviderFactory: ProductViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_display_product, container, false)
        viewModelProviderFactory = ProductViewModelFactory(get<ProductRepository>())
        productViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(ProductViewModel::class.java)
        productAdapter = ProductAdapter()

        mBinding.apply {
            viewModel = productViewModel
            lifecycleOwner = this@DisplayProductFragment
        }

        mBinding.rvProductDisplay.apply {
            this.adapter = productAdapter
            this.layoutManager = LinearLayoutManager(requireContext())
        }

        mBinding.btnRetry.setOnClickListener {
            productViewModel.getAllProductList()
        }
        setHasOptionsMenu(true)
        return mBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nav_menu, menu)

        // handle drop down menu for filter
        val filterList = productViewModel.getProductTypeFilterList().value
        if (filterList != null) {
            for (i in 0 until filterList.size) {
                menu.findItem(R.id.menu_filter).subMenu?.add(
                    R.id.fl_Fragment,
                    i,
                    Menu.NONE,
                    filterList[i]
                )
            }
        }

        val searchItem: MenuItem = menu.findItem(R.id.item_navSearch)
        val searchView: SearchView = searchItem.actionView as SearchView

        // handle search call back
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(qString: String): Boolean {
                searchProduct(qString)
                return true
            }

            override fun onQueryTextSubmit(qString: String): Boolean {
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId != R.id.menu_filter)
            item.title?.toString()?.let { productViewModel.handleFilter(it) }
        return true
    }

    private fun updateFilterList() {
        activity?.invalidateOptionsMenu()
    }


    // handle search option list
    private fun searchProduct(productName: String?) {

        if (productName != null) {
            val searchList = ArrayList<ProductResponseItem>()
            for (i in productViewModel.getProductList().value?.data as ArrayList<ProductResponseItem>) {
                if (i.product_name.lowercase(Locale.ROOT).contains(productName)) {
                    searchList.add(i)
                }
            }

            if (searchList.isEmpty()) {
                Toast.makeText(requireContext(), resources.getString(R.string.no_data_found), Toast.LENGTH_SHORT).show()
            } else {
                productAdapter.updateList(searchList)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        productViewModel.getAllProductList()

        productViewModel.getProductList().observe(this) {
            if (it.data == null)
                Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
            else {
                productAdapter.updateList(it.data as ArrayList<ProductResponseItem>)
                productViewModel.updateProductFilterList()
            }
        }

        productViewModel.getProductTypeFilterList().observe(this) {
            updateFilterList()
        }

        productViewModel.getUserFilterList().observe(this) {
            productAdapter.updateList(it)
        }

        val navHostFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.fl_Fragment) as NavHostFragment
        val navController = navHostFragment.navController
        mBinding.fbAddProduct.setOnClickListener {
            navController.navigate(R.id.action_displayProductFragment_to_addProductFragment)
        }
    }

    companion object {
        fun newInstance() = DisplayProductFragment()
    }
}