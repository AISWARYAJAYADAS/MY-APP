package com.example.myapplication.profile.model

import androidx.annotation.Keep

@Keep
data class Address(
    val buildingName: String,
    val city: String,
    val doorNumber: String,
    val postalCode: String,
    val province: String,
    val telephone: String
)