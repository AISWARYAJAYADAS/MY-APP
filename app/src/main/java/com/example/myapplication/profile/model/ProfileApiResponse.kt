package com.example.myapplication.profile.model

import androidx.annotation.Keep

@Keep
data class ProfileApiResponse(
    val profile: Profile,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)