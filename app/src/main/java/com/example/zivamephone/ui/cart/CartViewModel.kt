package com.example.zivamephone.ui.cart


import android.view.View

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.networkmodule.database.CartTable
import com.example.networkmodule.database.PhoneDao
import com.example.networkmodule.model.ProductsItem
import com.example.networkmodule.usecase.PhoneUseCase
import com.example.zivamephone.base.ViewState
import com.example.zivamephone.util.toLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CartViewModel  @Inject constructor(
    private val getPhoneUseCase: PhoneUseCase,
    private val phoneDao: PhoneDao
):ViewModel(){

    private val _viewState = MutableLiveData<ViewState>(ViewState.Idle)
    val viewState = _viewState.toLiveData()
    private val _phoneList=MutableLiveData<List<CartTable>>()
    val phoneList=_phoneList.toLiveData()

    fun getDataFromDb(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = phoneDao.getPhone()
            _phoneList.postValue(result)
            _viewState.postValue(ViewState.Success())
        }
    }
    fun updateToDB(id:String,quant:String){
        viewModelScope.launch(Dispatchers.IO) {
            // phoneDao.clear()
            phoneDao.update(quant,id)
        }
    }
    fun deleteFromDB(){
        viewModelScope.launch(Dispatchers.IO) {
            phoneDao.clear()
        }
    }
}

