package com.example.myapplication.api.datasource

import com.example.myapplication.api.ApiService
import com.example.myapplication.api.CommonApiSuccessResponse
import com.example.myapplication.api.model.Output
import com.example.myapplication.home.AddDeviceRequest
import com.example.myapplication.home.AddDeviceResponse
import com.example.myapplication.login.LoginApiResponse
import com.example.myapplication.login.LoginRequest
import com.example.myapplication.login.LogoutRequest
import com.example.myapplication.pref.SharedPref
import com.example.myapplication.profile.model.ProfileApiResponse
import com.example.myapplication.utils.NetworkConstants
import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject


class RemoteDataSource @Inject constructor(
    private val apiService: ApiService,
    private val sharedPrefs: SharedPref,
    retrofit: Retrofit,
) : BaseRemoteDataSource(retrofit) {

    suspend fun login(loginRequest: LoginRequest): Output<LoginApiResponse> {
        return getResponse(request = {
            apiService.login(
                request = loginRequest,
            )
        })
    }

    suspend fun logout(): Output<CommonApiSuccessResponse> {
        return getResponse(request = {
            apiService.logout(
                headers = sharedPrefs.headers,
                request = LogoutRequest(
                    appType = NetworkConstants.ANDROID,
                    appVersion = "1.0"

                )
            )
        })
    }

    suspend fun addDeviceRequest(
        deviceId: String,
        request: AddDeviceRequest
    ): Output<AddDeviceResponse> {
        return getResponse(request = {
            apiService.addDevice(
                headers = sharedPrefs.headers,
                deviceId = deviceId,
                request = request
            )
        })
    }

    suspend fun deleteDeviceRequest(
        deviceId: String
    ): Output<CommonApiSuccessResponse> {
        return getResponse(request = {
            apiService.deleteDevice(
                headers = sharedPrefs.headers,
                deviceId = deviceId
            )
        })
    }

    //Fetches the user's profile details.
    suspend fun getUserDetails():Output<ProfileApiResponse>{
        return getResponse (request = {
            apiService.getUserDetails(
                headers = sharedPrefs.headers
            )
        })
    }


}