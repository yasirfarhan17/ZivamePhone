package com.example.networkmodule.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Response(
	val products: List<ProductsItem?>? = null
) : Parcelable

@Parcelize
data class ProductsItem(
	val price: String? = null,
	val imageUrl: String? = null,
	val name: String? = null,
	val rating: Int? = null
) : Parcelable
