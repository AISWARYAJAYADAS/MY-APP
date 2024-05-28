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

/**
 * Class to handle methods in token expiry
 */
@Singleton
class TokenExpiryManager @Inject constructor(
    private val sharedPrefs: SharedPref,
    @ApplicationContext private val appContext: Context
){

    /**
     * Method to call the refresh access token API
     * This will create the request for refresh token api and calling api synchronously.
     * On success callback from api call saving tokens
     * On error, if INVALID_REFRESH_TOKEN error occurred logging out from app
     *
     * @return AccessToken - access token saved in preferences
     */
    suspend fun refreshToken(refreshTokenApi: RefreshTokenApiService): Pair<String,Int>? {
        val refreshToken = NetworkConstants.BEARER + sharedPrefs.getRefreshToken()
        val apiResponse =
            refreshTokenApi.refreshToken(
                refreshToken = refreshToken
            )
        if (apiResponse.isSuccessful) {
            apiResponse.body()?.data?.accessToken?.let {
                sharedPrefs.saveAccessToken(it)
                sharedPrefs.saveRefreshToken(apiResponse.body()?.data?.refreshToken ?: "")
                return Pair(it,200)
            }
        }
        val error = apiResponse.errorBody()?.errorCodeDetails()
        val statusCode = error?.first
        if (statusCode == INVALID_REFRESH_TOKEN || statusCode == AUTHENTICATION_ERROR) {
            forceLogoutUser(flow = LogoutFlow.REFRESH_TOKEN_EXPIRED)
            return Pair("",statusCode)
        }
        return null
    }

    /**
     * Method to clear all user related data and logout user forcefully
     */
    fun forceLogoutUser(flow: Int) {
        MainViewModel.forceLogoutUser(flowDetails = flow)
    }

    /**
     * check if token is available or not
     */
    fun isTokenAvailable(): Boolean {
        return sharedPrefs.getAccessToken().isNotEmpty()
    }

}