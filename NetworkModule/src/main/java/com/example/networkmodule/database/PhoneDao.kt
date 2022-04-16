package com.example.networkmodule.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface PhoneDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhone(phone: CartTable)

    @Query("delete from phone where 1")
    suspend fun clear()

    @Query("select * from phone")
    suspend fun getPhone():List<CartTable>

}