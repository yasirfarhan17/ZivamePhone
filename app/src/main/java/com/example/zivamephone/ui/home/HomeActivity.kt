package com.example.zivamephone.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.networkmodule.model.ProductsItem
import com.example.zivamephone.R
import com.example.zivamephone.databinding.ActivityMainBinding
import com.example.zivamephone.util.UiUtil
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

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
            rvHome.adapter=HomeAdapter()
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
}