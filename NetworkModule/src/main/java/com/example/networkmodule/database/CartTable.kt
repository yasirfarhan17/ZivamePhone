package com.example.networkmodule.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "phone")
data class CartTable (
    @ColumnInfo val price: String? = null,
    @ColumnInfo val image_url: String? = null,
    @PrimaryKey @ColumnInfo val name: String,
    @ColumnInfo val quantity:Int?=1
        )