package com.example.zivamephone.ui.CheckOut

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.zivamephone.R
import com.example.zivamephone.databinding.ActivityCheckOutBinding
import com.example.zivamephone.ui.home.HomeActivity
import com.example.zivamephone.ui.home.HomeAdapter

class checkOut : AppCompatActivity() {

    private lateinit var binding : ActivityCheckOutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_check_out)
        binding.continues.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))

        }
        binding.imgNormal.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
        }
    }
}