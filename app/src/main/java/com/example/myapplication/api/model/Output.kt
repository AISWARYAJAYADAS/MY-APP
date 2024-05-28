package com.example.myapplication.api.model

import androidx.annotation.Keep

/**
 * Generic class for holding success response, error response and loading status
 */
@Keep
sealed class Output<out T> {
    data class Success<out T : Any>(val value: T?) : Output<T>()
    data class Error(val apiError: ApiError) : Output<Nothing>()
}