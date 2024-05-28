package com.example.myapplication.login

import androidx.annotation.Keep

/**
 * Represents the response from a login API call.
 *
 * @property success Indicates whether the login was successful or not.
 * @property statusCode The status code returned by the server.
 * @property message The message returned by the server.
 * @property data The Login object containing the user's login details if the login was successful.
 */
@Keep
data class LoginApiResponse(
    val success: Boolean? = null,
    val statusCode: Int? = null,
    val message: String? = null,
    val data: Login? = null,
)

/**
 * Data class representing the Login response received from the server.
 *
 * @property accessToken The access token returned by the server for the authenticated user.
 * @property refreshToken The refresh token returned by the server for the authenticated user.
 * @property isProfileVerified Flag indicating whether the user's profile is verified or not.
 */
@Keep
data class Login(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val isProfileVerified: Boolean? = null,
)

/**
 * Data class representing a login request with the required fields.
 *
 * @property appType the type of the application
 * @property memberId the member ID of the user
 * @property appVersion the version of the application
 * @property password the password of the user
 * @property deviceId the device ID of the user
 */
@Keep
data class LoginRequest(
    val appType: String,
    val memberId: String,
   // val appVersion: String,
    val password: String,
    val deviceId: String,
)

/**
 * Represents the request body for the logout API.
 *
 * @property appType The type of the application.
 * @property appVersion The version of the application.
 */
@Keep
data class LogoutRequest(
    val appType: String,
    val appVersion: String,
)