package com.example.myapplication.api

import com.example.myapplication.api.model.RefreshTokenApiResponse
import com.example.myapplication.utils.ApiEndPoint
import com.example.myapplication.utils.NetworkConstants
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST

interface RefreshTokenApiService {
    @POST(ApiEndPoint.REFRESH)
    suspend fun refreshToken(
        @Header(NetworkConstants.AUTHORIZATION) refreshToken: String?,
    ): Response<RefreshTokenApiResponse>
}