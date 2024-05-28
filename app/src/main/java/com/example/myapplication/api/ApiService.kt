package com.example.myapplication.api

import androidx.annotation.Keep
import com.example.myapplication.home.AddDeviceRequest
import com.example.myapplication.home.AddDeviceResponse
import com.example.myapplication.login.LoginApiResponse
import com.example.myapplication.login.LoginRequest
import com.example.myapplication.login.LogoutRequest
import com.example.myapplication.utils.ApiEndPoint
import com.example.myapplication.utils.NetworkConstants
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST(ApiEndPoint.LOGIN)
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginApiResponse>

    @POST(ApiEndPoint.LOGOUT)
    suspend fun logout(
        @HeaderMap headers: Map<String, String>,
        @Body request: LogoutRequest
    ): Response<CommonApiSuccessResponse>

    @POST(ApiEndPoint.ADD_REMOVE_DEVICE)
    suspend fun addDevice(
        @HeaderMap headers: Map<String, String>,
        @Body request: AddDeviceRequest,
        @Path(NetworkConstants.DEVICE_ID) deviceId: String?,
    ): Response<AddDeviceResponse>

    @DELETE(ApiEndPoint.ADD_REMOVE_DEVICE)
    suspend fun deleteDevice(
        @HeaderMap headers: Map<String, String>,
        @Path(NetworkConstants.DEVICE_ID) deviceId: String?,
    ): Response<CommonApiSuccessResponse>
}








@Keep
data class CommonApiSuccessResponse(
    val success: Boolean? = null,
    val statusCode: Int? = null,
    val message: String? = null
)