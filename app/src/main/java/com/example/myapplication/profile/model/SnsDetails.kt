package com.example.myapplication.profile.model

import androidx.annotation.Keep

@Keep
data class SnsDetails(
    val dotstUrl: String,
    val instagramUrl: String,
    val tiktokUrl: Any,
    val twitterUrl: Any,
    val wearUrl: Any,
    val youtubeUrl: Any
)