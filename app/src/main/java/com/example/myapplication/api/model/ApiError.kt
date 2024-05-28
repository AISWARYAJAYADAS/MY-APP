package com.example.myapplication.api.model

import androidx.annotation.Keep
import com.example.myapplication.DotCApplication
import com.example.myapplication.R


/**
 * Default Error Entity class for Api service errors
 * @param statusCode the status code of service
 * @param statusMessage the message from api service
 */
@Keep
data class ApiError(
    val statusCode: Int = 0,
    val statusMessage: String? = DotCApplication.getString(R.string.common_network_error),
    val messageFromApi: String? = "",
    val errorMessageFromApi: String? = ""
)