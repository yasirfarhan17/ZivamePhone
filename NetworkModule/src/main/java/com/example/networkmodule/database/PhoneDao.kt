package com.example.networkmodule.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface PhoneDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhone(phone: CartTable)

    @Query("delete from phone")
    suspend fun clear()

    @Query("select * from phone")
    suspend fun getPhone():List<CartTable>

    @Query("UPDATE phone SET quantity = :quant WHERE name = :id")
    fun update(quant:String, id: String)

}