package com.example.zivamephone.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.example.networkmodule.api.PhoneApi
import com.example.networkmodule.database.PhoneDao
import com.example.networkmodule.database.PhoneDataBase
import com.example.networkmodule.network.HeaderInterceptor
import com.example.networkmodule.network.NetworkClient
import com.example.networkmodule.network.NetworkManager
import com.example.networkmodule.repository.PhoneRepository
import com.example.networkmodule.repository.PhoneRepositoryImpl
import com.example.networkmodule.usecase.PhoneUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit):PhoneApi{
        return retrofit.create(PhoneApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api:PhoneApi):PhoneRepository{
        return PhoneRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideNetworkManager(
        @ApplicationContext context:Context
    ):NetworkManager{
        val connectivityManager=context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return NetworkManager(connectivityManager)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        headerInterceptor: HeaderInterceptor,
        networkManager: NetworkManager
    ):OkHttpClient{
        return NetworkClient.provideOkHttp(
            headerInterceptor,
            networkManager
        )
    }

    @Singleton
    @Provides
    fun provideUseCase(
        repository: PhoneRepository
    ):PhoneUseCase{
        return PhoneUseCase((repository))
    }


    @Singleton
    @Provides
    fun provideBaseAndroidDatabase(
        @ApplicationContext context: Context
    ):PhoneDataBase{
        return Room.databaseBuilder(
            context,
            PhoneDataBase::class.java,
            PhoneDataBase.Name
        )
            .build()
    }

    @Singleton
    @Provides
    fun provideEmployeeDao(phoneDataBase: PhoneDataBase):PhoneDao{
        return phoneDataBase.phone
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ):Retrofit=NetworkClient.provideRetrofit(okHttpClient)


}