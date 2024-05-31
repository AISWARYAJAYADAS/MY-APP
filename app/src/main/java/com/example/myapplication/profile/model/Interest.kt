package com.example.myapplication.profile.model

import com.google.errorprone.annotations.Keep

@Keep
data class Interest(
    val code: String,
    val id: Int,
    val labelEn: String,
    val labelJp: String,
    val tags: List<Tag>
)