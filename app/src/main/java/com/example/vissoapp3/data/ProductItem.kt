package com.example.vissoapp3.data

import androidx.annotation.DrawableRes

data class ProductItem(
    val name: String,
    val brand: String,
    val price: Int,
    @DrawableRes val imageRes: Int
)