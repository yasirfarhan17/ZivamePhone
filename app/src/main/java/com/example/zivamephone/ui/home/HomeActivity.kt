package com.example.zivamephone.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.networkmodule.model.ProductsItem
import com.example.zivamephone.R
import com.example.zivamephone.databinding.ActivityMainBinding
import com.example.zivamephone.ui.cart.CartActivity
import com.example.zivamephone.util.UiUtil
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity(),PhoneAdapterCallBack {

    private val _viewModel:HomeViewModel by viewModels()
    private lateinit var uiUtil: UiUtil
    private lateinit var  binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)

       uiUtil=UiUtil((this))
        initUi()
        observeError()
        observeList()
    }

    private fun initUi(){
        with(binding){
            rvHome.layoutManager=StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
            rvHome.adapter=HomeAdapter(this@HomeActivity)
            appBarLayout.imgCart.setOnClickListener {
                val intent =Intent(this@HomeActivity,CartActivity::class.java)
                startActivity(intent)
            }
            _viewModel.getPhoneList()
        }

    }
    private fun observeError(){
        _viewModel.setError.observe(this){
            uiUtil.showMessage(it.toString())
        }
    }
    private fun observeList(){
        _viewModel.phoneList.observe(this){
            (binding.rvHome.adapter as HomeAdapter).submitList(list =it as ArrayList<ProductsItem>)
        }
    }

    override fun onItemClick(item: ProductsItem) {
        _viewModel.insertToDB(item)
        Toast.makeText(this,"Item Successfully Added",Toast.LENGTH_SHORT).show()
    }
}