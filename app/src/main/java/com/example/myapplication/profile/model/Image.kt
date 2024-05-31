package com.example.myapplication.profile.model

import androidx.annotation.Keep

@Keep
data class Image(
    val smallUrl: String,
    val thumbnailUrl: String
)