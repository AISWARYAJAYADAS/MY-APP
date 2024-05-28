package com.example.myapplication.api.model

import androidx.annotation.Keep

/**
 * Data class representing the response returned when attempting to refresh the access token.
 *
 * @property success Boolean indicating if the API call was successful.
 * @property statusCode The status code returned by the API.
 * @property message The message returned by the API.
 * @property data The [RefreshToken] object containing the new access and refresh tokens.
 */
@Keep
data class RefreshTokenApiResponse(
    val success: Boolean? = null,
    val statusCode: Int? = null,
    val message: String? = null,
    val data: RefreshToken? = null,
)

/**
 * Data class representing the new access and refresh tokens returned when attempting to refresh the access token.
 *
 * @property accessToken The new access token.
 * @property refreshToken The new refresh token.
 */
@Keep
data class RefreshToken(
    val accessToken: String?,
    val refreshToken: String?
)