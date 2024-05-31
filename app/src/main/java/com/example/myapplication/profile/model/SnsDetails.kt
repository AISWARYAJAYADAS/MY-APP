package com.example.myapplication.profile.model

import androidx.annotation.Keep

@Keep
data class SnsDetails(
    val instagramUrl: String? = null,
    val wearUrl: String? = null,
    val youtubeUrl: String? = null,
    val twitterUrl: String? = null,
    val tiktokUrl: String? = null,
    val dotstUrl: String? = null,
)