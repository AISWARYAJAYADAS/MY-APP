package com.example.myapplication.ui.common

import com.example.myapplication.api.datasource.RemoteDataSource
import com.example.myapplication.api.model.Output
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CommonRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) {

    fun fetchMaterConfig():Flow<Output<ConfigApiResponse>>{
        return flow {
            val result = remoteDataSource.fetchMasterConfig()
            emit(result)
        }.flowOn(Dispatchers.IO)
    }




}