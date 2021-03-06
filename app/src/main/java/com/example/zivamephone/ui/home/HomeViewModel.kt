package com.example.zivamephone.ui.home


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.networkmodule.database.PhoneDao
import com.example.networkmodule.model.ProductsItem
import com.example.networkmodule.network.Resource
import com.example.networkmodule.usecase.PhoneUseCase
import com.example.zivamephone.base.ViewState
import com.example.zivamephone.util.toLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class HomeViewModel  @Inject constructor(
    private val getPhone:PhoneUseCase,
    private val phoneDao: PhoneDao

):ViewModel(){
    private val _viewState=MutableLiveData<ViewState>(ViewState.Idle)
    val viewState=_viewState.toLiveData()
     var count :Int=0

    private val _phoneList=MutableLiveData<List<ProductsItem>>()
    val phoneList=_phoneList.toLiveData()

    private val _setError = MutableLiveData<String>()
    val setError = _setError.toLiveData()

    fun getPhoneList(){
        getPhone().onEach {
            _viewState.postValue(ViewState.Loading)
            when(it){
                is Resource.Success ->{
                    _phoneList.postValue(it.data?.products as List<ProductsItem>?)
                    Log.d("againcheck","${it.data?.products}")
                    _viewState.postValue(ViewState.Success())
                }
                is Resource.Error ->{
                    _setError.postValue(it.message)
                    _viewState.postValue(ViewState.Error())
                }
                is Resource.Loading ->{
                    _viewState.postValue(ViewState.Loading)
                }
            }
        }.launchIn(viewModelScope + exceptionHandler)
    }
    private fun handleFailure(throwable: Throwable?){
        _viewState.postValue(ViewState.Error(throwable))
        Log.e("Network_Error",throwable.toString())
    }
    private val exceptionHandler = CoroutineExceptionHandler{_,exception ->
        handleFailure(throwable = exception)
    }

    fun insertToDB(responseItem: ProductsItem){
        viewModelScope.launch(Dispatchers.IO) {
           // phoneDao.clear()
            val list=responseItem.toCartTable()
            val dao=phoneDao.insertPhone(list)
        }
    }
     suspend fun countFromDB():Int = withContext(Dispatchers.IO){
             count = phoneDao.getCount()!!
             Log.d("dbcount1", count.toString())
             return@withContext count
     }
    }