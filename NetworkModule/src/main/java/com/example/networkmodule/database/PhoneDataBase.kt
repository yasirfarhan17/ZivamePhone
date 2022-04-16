package com.example.networkmodule.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [CartTable::class],version = PhoneDataBase.Version)
abstract class PhoneDataBase :RoomDatabase() {
    abstract val phone:PhoneDao

    companion object{
        const val Name="phone_dataBase"
        const val Version=2

        @Volatile
        private var Instance :PhoneDataBase?=null

        fun getInstance(context: Context):PhoneDataBase{
            val tempInstance= Instance
            if(tempInstance!=null)
                return tempInstance

            synchronized(this){
                val instance= Room.databaseBuilder(
                    context,
                    PhoneDataBase::class.java,
                    Name
                )
                    .build()
                Instance=instance
                return instance
            }
        }

    }
}