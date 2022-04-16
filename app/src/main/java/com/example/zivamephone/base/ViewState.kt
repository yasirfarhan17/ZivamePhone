package com.example.zivamephone.base

sealed class ViewState {
    object Idle: ViewState()
    object Loading: ViewState()
    object StopLoading: ViewState()
    object StartLoading: ViewState()
    data class Success(val message:String?=null) : ViewState()
    data class Error(val throwable: Throwable?=null) : ViewState()

}