package com.example.myapplication.login

import com.example.myapplication.api.datasource.RemoteDataSource
import com.example.myapplication.api.model.Output
import com.example.myapplication.login.LoginApiResponse
import com.example.myapplication.login.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginRepository @Inject constructor(private val remoteDataSource: RemoteDataSource){

    fun login(loginRequest: LoginRequest): Flow<Output<LoginApiResponse>> {
        return flow {
            val result = remoteDataSource.login(loginRequest = loginRequest)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

}