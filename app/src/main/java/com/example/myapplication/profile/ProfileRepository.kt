package com.example.myapplication.profile

import android.util.Log
import com.example.myapplication.api.CommonApiSuccessResponse
import com.example.myapplication.api.datasource.RemoteDataSource
import com.example.myapplication.api.model.Output
import com.example.myapplication.home.AddDeviceRequest
import com.example.myapplication.home.AddDeviceResponse
import com.example.myapplication.profile.model.ProfileApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {

    fun logout(): Flow<Output<CommonApiSuccessResponse>> {
        return flow {
            val result = remoteDataSource.logout()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun addDevice(
        request: AddDeviceRequest,
        deviceId: String,
    ): Flow<Output<AddDeviceResponse>> {
        return flow {
            val result = remoteDataSource.addDeviceRequest(
                request = request,
                deviceId = deviceId
            )
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun removeDevice(deviceId: String) : Flow<Output<CommonApiSuccessResponse>>{
        return flow {
            val result = remoteDataSource.deleteDeviceRequest(
                deviceId = deviceId
            )
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    fun getUserDetails():Flow<Output<ProfileApiResponse>>{
        return flow {
            val result = remoteDataSource.getUserDetails()
            Log.d("Profile Repository", "getUserDetails: $result")
            emit(result)
        }.flowOn(Dispatchers.IO)
    }




}