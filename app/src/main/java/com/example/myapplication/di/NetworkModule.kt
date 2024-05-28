package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.MainViewModel
import com.example.myapplication.api.ApiService
import com.example.myapplication.api.RefreshTokenApiService
import com.example.myapplication.pref.SharedPref
import com.example.myapplication.utils.ApiEndPoint
import com.example.myapplication.utils.NetworkConstants
import com.example.myapplication.utils.StatusCode
import com.example.myapplication.utils.StatusCode.AUTHENTICATION_ERROR
import com.example.myapplication.utils.TempConstants
import com.example.myapplication.utils.manager.TokenExpiryManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }


    @Provides
    fun provideOkHttpClient(
        tokenExpiryManager: TokenExpiryManager,
        refreshTokenApi: RefreshTokenApiService,
        @ApplicationContext appContext: Context
    ): OkHttpClient {
        return createOkHttpClient(tokenExpiryManager, refreshTokenApi, appContext)
    }


    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(TempConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
           .client(okHttpClient)
            .build()
    }


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideRefreshTokenApiService(
        @ApplicationContext appContext: Context
    ): RefreshTokenApiService {
        return Retrofit.Builder()
            .baseUrl(TempConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttpClient(appContext).build())
            .build().create(RefreshTokenApiService::class.java)
    }


    @Provides
    @Singleton
    fun provideTokenExpiryManager(
        sharedPrefs: SharedPref,
        @ApplicationContext appContext: Context
    ): TokenExpiryManager {
        return TokenExpiryManager(sharedPrefs,appContext)
    }


    private fun getOkHttpClient(appContext: Context): OkHttpClient.Builder {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(provideHTTPLoggingInterceptor())
        return httpClient
    }


    private fun createOkHttpClient(
        tokenExpiryManager: TokenExpiryManager,
        refreshTokenApi: RefreshTokenApiService,
        appContext: Context
    ): OkHttpClient {
        return getOkHttpClient(appContext).readTimeout(
            NetworkConstants.READ_TIMEOUT,
            TimeUnit.MINUTES
        )
            .connectTimeout(
                NetworkConstants.CONNECTION_TIMEOUT,
                TimeUnit.MINUTES
            )  //write timeout, used for increasing the timeout to send the data in API request, default is 10 seconds
            .writeTimeout(NetworkConstants.WRITE_TIMEOUT, TimeUnit.MINUTES)
            .addInterceptor { chain ->
                var request = chain.request()
                var response = chain.proceed(request)
                val urlPath = request.url.toUrl().path

                /* // If the secret key passed in the header is expired, force logout the user.
                 if (response.isSuccessful.not() && urlPath.contains(ApiEndPoint.LOGIN).not() &&
                     (response.code == HttpURLConnection.HTTP_BAD_REQUEST || response.code == HttpURLConnection.HTTP_FORBIDDEN)
                 ) {
                     val errorCode = checkForErrorStatusCode(response)
                     if (errorCode == StatusCode.INACTIVE_USER) {
                         tokenExpiryManager.forceLogoutUser(flow = LogoutFlow.INACTIVE_USER)
                         return@addInterceptor response
                     }
                 }*/

                // Variable to hold the retry count for each apis
                var retryCount = 0

                // Handle token expiry
                //Checking if an unauthorized error happened and looping till retry count
                while (response.isSuccessful.not() && response.code == HttpURLConnection.HTTP_UNAUTHORIZED &&
                    !urlPath.contains(ApiEndPoint.REFRESH) && tokenExpiryManager.isTokenAvailable()
                ) {
                    var accessToken: String?
                    var accessTokenApiCode: Int?
                    runBlocking {
                        val tokenDetails = tokenExpiryManager.refreshToken(refreshTokenApi)
                        accessToken = tokenDetails?.first
                        accessTokenApiCode = tokenDetails?.second
                    }
                    //Increments api retry count
                    retryCount++
                    // If the retry count hits its maximum, reset retry count and send broadcast to logout the user
                    if ((accessTokenApiCode == StatusCode.INVALID_REFRESH_TOKEN || accessTokenApiCode == AUTHENTICATION_ERROR) || retryCount == NetworkConstants.MAXIMUM_API_RETRY_COUNT) {
                        response.code
                        return@addInterceptor response
                    }
                    if (accessToken.isNullOrBlank().not()) {
                        // If updated token is not null, create new header and call the current api
                        request = request.newBuilder()
                            .header(
                                NetworkConstants.AUTHORIZATION,
                                NetworkConstants.BEARER + accessToken
                            )
                            .build()

                        // retry the request
                        response.close()
                        response = chain.proceed(request)
                    }
                }
                return@addInterceptor response
            }
            .build()
    }


    /**
     * This method checks for the error status code returned in the response body.
     * If the response code is INACTIVE_USER, it returns the corresponding status code.
     * If any other error occurs or response body is empty, it returns null.
     * @param response the response received from the API call
     * @return the error status code or null if no error found
     */
    /* private fun checkForErrorStatusCode(response: Response): Int? {
         try {
             val responseBody: ResponseBody = response.peekBody(Long.MAX_VALUE)
             val errorDetails = responseBody.errorCodeDetails()
             val statusCode = errorDetails?.first
             if (statusCode == StatusCode.INACTIVE_USER) {
                 return StatusCode.INACTIVE_USER
             }
         } catch (ex: Exception) {
             return null
         }
         return null
     }*/
}