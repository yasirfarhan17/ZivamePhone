package com.example.zivamephone.ui.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.networkmodule.database.CartTable
import com.example.zivamephone.R
import com.example.zivamephone.databinding.ActivityCartBinding
import com.example.zivamephone.ui.CheckOut.checkOut
import com.example.zivamephone.util.UiUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : AppCompatActivity(),CartAdapterCallBack {

    private lateinit var binding: ActivityCartBinding
    private val _viewModel :CartViewModel by viewModels()

    private lateinit var uiUtil: UiUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_cart)

        uiUtil= UiUtil(this)
        initUi()
        addObserver()

    }



    private fun initUi() {
        with(binding){
            rvHome.layoutManager=LinearLayoutManager(this@CartActivity)
            rvHome.adapter=CartAdapter(this@CartActivity)
            _viewModel.getDataFromDb()

            checkout.setOnClickListener {
                rvHome.visibility=View.GONE
                checkout.visibility=View.GONE
                loading.visibility=View.VISIBLE
                tvLoading.visibility=View.VISIBLE
                Handler().postDelayed({
                    startActivity(Intent(this@CartActivity,checkOut::class.java))
                    finish()

                },3000)

                _viewModel.deleteFromDB()
            }


        }
    }
    private fun addObserver() {
        observeList()
    }



    private fun observeList() {
        _viewModel.phoneList.observe(this){
            (binding.rvHome.adapter as CartAdapter).submitList(list =  it as ArrayList<CartTable>)
        }
    }

    override fun onItemClick(quant: String, id: String) {
        _viewModel.updateToDB(id,quant)
    }


}