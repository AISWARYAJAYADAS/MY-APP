package com.example.myapplication.home

import androidx.annotation.Keep

@Keep
data class AddDeviceRequest(
    val operatingSystem: String?,
    val deviceToken: String?
)

@Keep
data class AddDeviceResponse(
    val success: Boolean? = null,
    val statusCode: Int? = null,
    val message: String? = null,
    val data: DeviceData? = null,
)

@Keep
data class DeviceData(
    val id:Int? = null
)