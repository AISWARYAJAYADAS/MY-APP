package com.example.myapplication.profile.model

import androidx.annotation.Keep

@Keep
data class Permission(
    val isBuyer: Boolean,
    val isSeller: Boolean
)