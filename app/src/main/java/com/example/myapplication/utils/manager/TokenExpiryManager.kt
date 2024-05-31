package com.example.myapplication.utils.manager

import android.content.Context
import com.example.myapplication.MainViewModel
import com.example.myapplication.api.RefreshTokenApiService
import com.example.myapplication.pref.SharedPref
import com.example.myapplication.utils.LogoutFlow
import com.example.myapplication.utils.NetworkConstants
import com.example.myapplication.utils.StatusCode.AUTHENTICATION_ERROR
import com.example.myapplication.utils.StatusCode.INVALID_REFRESH_TOKEN
import com.example.myapplication.utils.extension.errorCodeDetails
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TokenExpiryManager @Inject constructor(
    private val sharedPrefs: SharedPref,
    private val refreshTokenApi: RefreshTokenApiService
) {

    // Data class to hold access token and status code
    data class TokenResponse(val accessToken: String, val statusCode: Int)

    suspend fun refreshToken(): TokenResponse? {
        val refreshToken = sharedPrefs.getRefreshToken()

        // Call API to refresh token
        val apiResponse = refreshTokenApi.refreshToken(refreshToken)

        return if (apiResponse.isSuccessful) {
            // Handle successful response
            apiResponse.body()?.data?.let { response ->
                response.accessToken?.let { sharedPrefs.saveAccessToken(it) }
                response.refreshToken?.let { sharedPrefs.saveRefreshToken(it) }
                response.accessToken?.let { TokenResponse(it, 200) }
            }
        } else {
            // Handle unsuccessful response
            val statusCode = apiResponse.code()
            if (statusCode == 401 || statusCode == 403) {
                sharedPrefs.clear()
            }
            // Handle other errors or return a specific error object here (Optional)
            null
        }
    }

    fun isTokenAvailable(): Boolean = sharedPrefs.getAccessToken().isNotEmpty()
}


//@Singleton
//class TokenExpiryManager @Inject constructor(
//    private val sharedPrefs: SharedPref,
//    private val refreshTokenApi: RefreshTokenApiService
//) {
//
//    // Data class to hold access token and status code
//    data class TokenResponse(val accessToken: String, val statusCode: Int)
//
//    suspend fun refreshToken(): TokenResponse? {
//        val refreshToken = sharedPrefs.getRefreshToken()
//
//        // Call API to refresh token
//        val apiResponse = refreshTokenApi.refreshToken(refreshToken)
//
//        return if (apiResponse.isSuccessful) {
//            // Handle successful response
//            apiResponse.body()?.data?.let { response ->
//                response.accessToken?.let { sharedPrefs.saveAccessToken(it) }
//                response.refreshToken?.let { sharedPrefs.saveRefreshToken(it) }
//                response.accessToken?.let { TokenResponse(it, 200) }
//            }
//        } else {
//            // Handle unsuccessful response
//            val statusCode = apiResponse.code()
//            if (statusCode == 401 || statusCode == 403) {
//                sharedPrefs.clear()
//            }
//            // Handle other errors or return a specific error object here (Optional)
//            null
//        }
//    }
//
//    fun isTokenAvailable(): Boolean = sharedPrefs.getAccessToken().isNotEmpty()
//}


//@Singleton
//class TokenExpiryManager @Inject constructor(
//    private val sharedPrefs: SharedPref,
//    private val refreshTokenApi: RefreshTokenApiService
//) {
//
//    suspend fun refreshToken(): Pair<String, Int>? {
//        val refreshToken = sharedPrefs.getRefreshToken()
//        val apiResponse = refreshTokenApi.refreshToken(refreshToken)
//
//        return if (apiResponse.isSuccessful) {
//            apiResponse.body()?.let {
//                it.data?.accessToken?.let { it1 -> sharedPrefs.saveAccessToken(it1) }
//                it.data?.refreshToken?.let { it1 -> sharedPrefs.saveRefreshToken(it1) }
//                Pair(it.accessToken, 200)
//            }
//        } else {
//            val statusCode = apiResponse.code()
//            if (statusCode == 401 || statusCode == 403) {
//                // Force logout user
//                sharedPrefs.clear()
//            }
//            null
//        }
//    }
//
//    fun isTokenAvailable(): Boolean = sharedPrefs.getAccessToken().isNotEmpty()
//}


///**
// * Class to handle methods in token expiry
// */
//@Singleton
//class TokenExpiryManager @Inject constructor(
//    private val sharedPrefs: SharedPref,
//    @ApplicationContext private val appContext: Context
//){
//
//    suspend fun refreshToken(refreshTokenApi: RefreshTokenApiService): String? {
//        val refreshToken = sharedPrefs.getRefreshToken()
//        val response = refreshTokenApi.refreshToken(refreshToken)
//        return if (response.isSuccessful) {
//            val newAccessToken = response.body()?.data?.accessToken
//            if (newAccessToken != null) {
//                sharedPrefs.saveAccessToken(newAccessToken)
//                newAccessToken
//            } else {
//                null
//            }
//        } else {
//            null
//        }
//    }
//
//    fun clearTokens() {
//        sharedPrefs.clearTokens()
//    }
//
//
////    suspend fun refreshToken(refreshTokenApi: RefreshTokenApiService): Pair<String,Int>? {
////        val refreshToken = NetworkConstants.BEARER + sharedPrefs.getRefreshToken()
////        val apiResponse =
////            refreshTokenApi.refreshToken(
////                refreshToken = refreshToken
////            )
////        if (apiResponse.isSuccessful) {
////            apiResponse.body()?.data?.accessToken?.let {
////                sharedPrefs.saveAccessToken(it)
////                sharedPrefs.saveRefreshToken(apiResponse.body()?.data?.refreshToken ?: "")
////                return Pair(it,200)
////            }
////        }
////        val error = apiResponse.errorBody()?.errorCodeDetails()
////        val statusCode = error?.first
////        if (statusCode == INVALID_REFRESH_TOKEN || statusCode == AUTHENTICATION_ERROR) {
////            forceLogoutUser(flow = LogoutFlow.REFRESH_TOKEN_EXPIRED)
////            return Pair("",statusCode)
////        }
////        return null
////    }
//
//    /**
//     * Method to clear all user related data and logout user forcefully
//     */
//    fun forceLogoutUser(flow: Int) {
//        MainViewModel.forceLogoutUser(flowDetails = flow)
//    }
//
//    /**
//     * check if token is available or not
//     */
//    fun isTokenAvailable(): Boolean {
//        return sharedPrefs.getAccessToken().isNotEmpty()
//    }
//
//}