package com.example.networkmodule.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor():Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return "prefsUtil".let {
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .header("Authorization","Bearer $it")
                    .build()
            )
        }
    }

}