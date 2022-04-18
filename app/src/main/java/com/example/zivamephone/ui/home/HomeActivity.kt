package com.example.zivamephone.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.networkmodule.model.ProductsItem
import com.example.zivamephone.R
import com.example.zivamephone.databinding.ActivityMainBinding
import com.example.zivamephone.ui.CheckOut.checkOut
import com.example.zivamephone.ui.cart.CartActivity
import com.example.zivamephone.util.UiUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


@AndroidEntryPoint
class HomeActivity : AppCompatActivity(),PhoneAdapterCallBack {

    private val _viewModel:HomeViewModel by viewModels()
    private lateinit var uiUtil: UiUtil
    private lateinit var  binding : ActivityMainBinding
    lateinit var job: Job

     var count:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)

       uiUtil=UiUtil((this))

        observeError()
        observeList()
        initUi()
        addListener()

    }

    override fun onBackPressed() {
        exitApp()
    }

    private fun initUi() {
        with(binding) {

            rvHome.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

            rvHome.visibility = View.GONE
            shimmer.visibility = View.VISIBLE
            _viewModel.getPhoneList()
            rvHome.adapter = HomeAdapter(this@HomeActivity)

            Handler().postDelayed({
                shimmer.visibility = View.GONE
                rvHome.visibility = View.VISIBLE
            }, 3000)


            appBarLayout.imgCart.setOnClickListener {
                val intent = Intent(this@HomeActivity, CartActivity::class.java)
                startActivity(intent)
            }

            GlobalScope.launch {
                Log.d("dbcount", _viewModel.countFromDB().toString())
                binding.appBarLayout.notificationBadge.text = _viewModel.countFromDB().toString()
            }


        }



    }
    private fun exitApp() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Close App?")
        builder.setIcon(R.mipmap.ic_launcher)
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            val a = Intent(Intent.ACTION_MAIN)
            a.addCategory(Intent.CATEGORY_HOME)
            a.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(a)


        }
        builder.setNegativeButton("No") { dialogInterface, which ->
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
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

    fun restoreAdapter(){
        (binding.rvHome.adapter as HomeAdapter).restoreAllList()
        binding.appBarLayout.clSearch.visibility = View.GONE
        binding.appBarLayout.clNormal.visibility = View.VISIBLE
    }


    private fun addListener() {

        binding.appBarLayout.imgSearch.setOnClickListener {
            binding.appBarLayout.clSearch.visibility = View.VISIBLE
            binding.appBarLayout.clNormal.visibility = View.GONE

        }
        binding.appBarLayout.imgCut.setOnClickListener {
            restoreAdapter()
        }

        binding.appBarLayout.imgNormal.setOnClickListener {
            restoreAdapter()
        }

        binding.appBarLayout.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                (binding.rvHome.adapter as HomeAdapter).filter.filter(p0.toString());
            }

            override fun afterTextChanged(p0: Editable?) {}
        })

    }

    override fun onItemClick(item: ProductsItem) {

        _viewModel.insertToDB(item)
        job=GlobalScope.launch {
            delay(500)

            Log.d("checkk",_viewModel.countFromDB().toString())
            binding.appBarLayout.notificationBadge.text= _viewModel.countFromDB().toString()

        }

        Toast.makeText(this,"Item Added Successfully",Toast.LENGTH_SHORT).show()
    }
}