package com.example.myapplication.api.datasource


import com.example.myapplication.DotCApplication
import com.example.myapplication.R
import com.example.myapplication.api.model.ApiError
import com.example.myapplication.api.model.Output
import com.example.myapplication.utils.extension.errorCodeDetails
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import java.net.SocketException
import javax.inject.Inject

/**
 * Abstract class for defining a base remote data source for making network requests using Retrofit.
 * This class includes methods for parsing the API response and converting error response to requested type.
 *
 * @param retrofit the Retrofit instance to use for making API requests.
 */
abstract class BaseRemoteDataSource constructor(private val retrofit: Retrofit) {

    /**
     * Method to parse the Response of API Service
     * @param T the type of Response
     * @param request
     * @return Output<T> the result of the request with type T
     */
    suspend fun <T> getResponse(request: suspend () -> Response<T>): Output<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                return if (null == result.body()) {
                    Output.Error(ApiError(statusCode = 0))
                } else {
                    Output.Success(result.body())
                }
            } else {
                val errorResponse = parseError(result)
                Output.Error(errorResponse)
            }
        } catch (e: IOException) {
            Output.Error(ApiError(statusMessage = DotCApplication.getString(R.string.no_network_connection)))
        } catch (e: SocketException) {
            Output.Error(ApiError())
        } catch (e: HttpException) {
            Output.Error(ApiError())
        } catch (e: Exception) {
            Output.Error(ApiError(statusMessage = e.message))
        }
    }

    /**
     * Method to convert the error response of API Service to requested type
     * @param response the response of requested api
     * @return the ApiError of the request
     */
    private fun parseError(response: Response<*>): ApiError {
        return try {
            val errorBody = response.errorBody()?.errorCodeDetails()
            val statusCode = errorBody?.first
            val statusMessage = errorBody?.second
            val errorMessageFromApi = errorBody?.third
            if (statusCode != null) {
                /*if (statusCode == NO_PERMISSION) {
                    ApiError(
                        statusCode = statusCode,
                        statusMessage = DotCApplication.getString(R.string.no_permission_common),
                        messageFromApi = statusMessage,
                        errorMessageFromApi = errorMessageFromApi
                    )
                } else {*/
                ApiError(
                    statusCode = statusCode,
                    messageFromApi = statusMessage,
                    errorMessageFromApi = errorMessageFromApi
                )
                //}
            } else {
                ApiError(messageFromApi = statusMessage, errorMessageFromApi = errorMessageFromApi)
            }
        } catch (e: IOException) {
            ApiError(statusMessage = DotCApplication.getString(R.string.no_network_connection))
        } catch (e: SocketException) {
            ApiError()
        } catch (e: HttpException) {
            ApiError()
        } catch (e: Exception) {
            ApiError()
        }
    }
}