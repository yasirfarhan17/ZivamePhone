package com.example.zivamephone.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.zivamephone.R
import com.example.zivamephone.util.UiUtil
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val _viewModel:HomeViewModel by viewModels()
    private lateinit var uiUtil: UiUtil
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       uiUtil=UiUtil((this))
        initUi()
        observeError()
        observeList()
    }

    private fun initUi(){
        _viewModel.getPhoneList()
    }
    private fun observeError(){
        _viewModel.setError.observe(this){
            uiUtil.showMessage(it.toString())
        }
    }
    private fun observeList(){
        _viewModel.phoneList.observe(this){
            Log.d("checkk","${it.products}")
        }
    }
}