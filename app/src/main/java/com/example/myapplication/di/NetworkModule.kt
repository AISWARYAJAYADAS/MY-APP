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
import javax.inject.Named
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    @Provides
    @Singleton
    @Named("ApiServiceClient")
    fun provideOkHttpClientForApiService(
        tokenManager: TokenExpiryManager
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                var request = chain.request()
                var response = chain.proceed(request)
                val urlPath = request.url.toUrl().path

                var retryCount = 0

                while (!response.isSuccessful && response.code == HttpURLConnection.HTTP_UNAUTHORIZED &&
                    !urlPath.contains(ApiEndPoint.REFRESH) && tokenManager.isTokenAvailable()
                ) {
                    val tokenDetails = runBlocking { tokenManager.refreshToken() }
                    val accessToken = tokenDetails?.accessToken

                    retryCount++
                    if ((tokenDetails?.statusCode == StatusCode.INVALID_REFRESH_TOKEN || tokenDetails?.statusCode == AUTHENTICATION_ERROR) || retryCount == NetworkConstants.MAXIMUM_API_RETRY_COUNT) {
                        return@addInterceptor response
                    }
                    if (!accessToken.isNullOrBlank()) {
                        request = request.newBuilder()
                            .header(NetworkConstants.AUTHORIZATION, NetworkConstants.BEARER + accessToken)
                            .build()

                        response.close()
                        response = chain.proceed(request)
                    }
                }
                response
            }
            .addInterceptor(provideHTTPLoggingInterceptor())
            .build()
    }

    @Provides
    @Singleton
    @Named("RefreshTokenClient")
    fun provideOkHttpClientForRefreshTokenApi(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(@Named("ApiServiceClient") okHttpClient: OkHttpClient): Retrofit {
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
        @Named("RefreshTokenClient") okHttpClient: OkHttpClient
    ): RefreshTokenApiService {
        return Retrofit.Builder()
            .baseUrl(TempConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(RefreshTokenApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideTokenExpiryManager(
        sharedPrefs: SharedPref,
        refreshTokenApi: RefreshTokenApiService
    ): TokenExpiryManager {
        return TokenExpiryManager(sharedPrefs, refreshTokenApi)
    }
}





//
//@Module
//@InstallIn(SingletonComponent::class)
//object NetworkModule {
//
//    @Provides
//    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//        return interceptor
//    }
//
//
//    @Provides
//    fun provideOkHttpClientForApiService(
//        tokenManager: TokenExpiryManager,
//        refreshTokenApi: RefreshTokenApiService,
//        @ApplicationContext context: Context
//    ): OkHttpClient {
//        return OkHttpClient.Builder()
//            .addInterceptor { chain ->
//                var request = chain.request()
//                var response = chain.proceed(request)
//                val urlPath = request.url.toUrl().path
//
//                // Variable to hold the retry count for each apis
//                var retryCount = 0
//
//                // Handle token expiry
//                //Checking if an unauthorized error happened and looping till retry count
//                while (response.isSuccessful.not() && response.code == HttpURLConnection.HTTP_UNAUTHORIZED &&
//                    !urlPath.contains(ApiEndPoint.REFRESH) && tokenManager.isTokenAvailable()
//                ) {
//                    var accessToken: String?
//                    var accessTokenApiCode: Int?
//                    runBlocking {
//                        val tokenDetails = tokenManager.refreshToken()
//                        accessToken = tokenDetails?.accessToken
//                        accessTokenApiCode = tokenDetails?.statusCode
//                    }
//                    //Increments api retry count
//                    retryCount++
//                    // If the retry count hits its maximum, reset retry count and send broadcast to logout the user
//                    if ((accessTokenApiCode == StatusCode.INVALID_REFRESH_TOKEN || accessTokenApiCode == AUTHENTICATION_ERROR) || retryCount == NetworkConstants.MAXIMUM_API_RETRY_COUNT) {
//                        response.code
//                        return@addInterceptor response
//                    }
//                    if (accessToken.isNullOrBlank().not()) {
//                        // If updated token is not null, create new header and call the current api
//                        request = request.newBuilder()
//                            .header(
//                                NetworkConstants.AUTHORIZATION,
//                                NetworkConstants.BEARER + accessToken
//                            )
//                            .build()
//
//                        // retry the request
//                        response.close()
//                        response = chain.proceed(request)
//                    }
//                }
//                return@addInterceptor response
//            }
//            .addInterceptor(provideHTTPLoggingInterceptor())
//            .build()
//    }
//
//
//    @Provides
//    fun provideOkHttpClientForRefreshTokenApi(
//        @ApplicationContext appContext: Context
//    ): OkHttpClient {
//        return OkHttpClient.Builder()
//            .addInterceptor(provideHTTPLoggingInterceptor())
//            .build()
//    }
//
//
//    @Provides
//    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(TempConstants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
//            .build()
//    }
//
//
//    @Provides
//    @Singleton
//    fun provideApiService(retrofit: Retrofit): ApiService {
//        return retrofit.create(ApiService::class.java)
//    }
//
//
//    @Provides
//    @Singleton
//    fun provideRefreshTokenApiService(
//        okHttpClient: OkHttpClient,
//        @ApplicationContext appContext: Context
//    ): RefreshTokenApiService {
//        return Retrofit.Builder()
//            .baseUrl(TempConstants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient)
//            .build().create(RefreshTokenApiService::class.java)
//    }
//
//
//    }







//
//@Module
//@InstallIn(SingletonComponent::class)
//object NetworkModule {
//
//    @Provides
//    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
//        return interceptor
//    }
//
//
//    @Provides
//    fun provideOkHttpClient(
//        tokenManager: TokenExpiryManager,
//        refreshTokenApi: RefreshTokenApiService,
//        @ApplicationContext context: Context
//    ): OkHttpClient {
//        return OkHttpClient.Builder()
//            .addInterceptor { chain ->
//                var request = chain.request()
//                val response = chain.proceed(request)
//                if (response.code == 401) {
//                    // Handle token refresh
//                    val newToken = tokenManager.refreshToken(refreshTokenApi)
//                    if (newToken != null) {
//                        request = request.newBuilder()
//                            .header("Authorization", "Bearer $newToken")
//                            .build()
//                        chain.proceed(request)
//                    } else {
//                        response
//                    }
//                } else {
//                    response
//                }
//            }
//            .addInterceptor(provideHTTPLoggingInterceptor())
//            .build()
//    }
//
//
////    @Provides
////    fun provideOkHttpClient(
////        tokenExpiryManager: TokenExpiryManager,
////        refreshTokenApi: RefreshTokenApiService,
////        @ApplicationContext appContext: Context
////    ): OkHttpClient {
////        return createOkHttpClient(tokenExpiryManager, refreshTokenApi, appContext)
////    }
//
//
//    @Provides
//    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(TempConstants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//           .client(okHttpClient)
//            .build()
//    }
//
//
//    @Provides
//    @Singleton
//    fun provideApiService(retrofit: Retrofit): ApiService {
//        return retrofit.create(ApiService::class.java)
//    }
//
//
//    @Provides
//    @Singleton
//    fun provideRefreshTokenApiService(
//        @ApplicationContext appContext: Context
//    ): RefreshTokenApiService {
//        return Retrofit.Builder()
//            .baseUrl(TempConstants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(getOkHttpClient(appContext).build())
//            .build().create(RefreshTokenApiService::class.java)
//    }
//
//
//    @Provides
//    @Singleton
//    fun provideTokenExpiryManager(
//        sharedPrefs: SharedPref,
//        @ApplicationContext appContext: Context
//    ): TokenExpiryManager {
//        return TokenExpiryManager(sharedPrefs,appContext)
//    }
//
//
//    private fun getOkHttpClient(appContext: Context): OkHttpClient.Builder {
//        val httpClient = OkHttpClient.Builder()
//        httpClient.addInterceptor(provideHTTPLoggingInterceptor())
//        return httpClient
//    }
//
//
//    private fun createOkHttpClient(
//        tokenExpiryManager: TokenExpiryManager,
//        refreshTokenApi: RefreshTokenApiService,
//        appContext: Context
//    ): OkHttpClient {
//        return getOkHttpClient(appContext).readTimeout(
//            NetworkConstants.READ_TIMEOUT,
//            TimeUnit.MINUTES
//        )
//            .connectTimeout(
//                NetworkConstants.CONNECTION_TIMEOUT,
//                TimeUnit.MINUTES
//            )  //write timeout, used for increasing the timeout to send the data in API request, default is 10 seconds
//            .writeTimeout(NetworkConstants.WRITE_TIMEOUT, TimeUnit.MINUTES)
//            .addInterceptor { chain ->
//                var request = chain.request()
//                var response = chain.proceed(request)
//                val urlPath = request.url.toUrl().path
//
//                // Variable to hold the retry count for each apis
//                var retryCount = 0
//
//                // Handle token expiry
//                //Checking if an unauthorized error happened and looping till retry count
//                while (response.isSuccessful.not() && response.code == HttpURLConnection.HTTP_UNAUTHORIZED &&
//                    !urlPath.contains(ApiEndPoint.REFRESH) && tokenExpiryManager.isTokenAvailable()
//                ) {
//                    var accessToken: String?
//                    var accessTokenApiCode: Int?
//                    runBlocking {
//                        val tokenDetails = tokenExpiryManager.refreshToken(refreshTokenApi)
//                        accessToken = tokenDetails?.first
//                        accessTokenApiCode = tokenDetails?.second
//                    }
//                    //Increments api retry count
//                    retryCount++
//                    // If the retry count hits its maximum, reset retry count and send broadcast to logout the user
//                    if ((accessTokenApiCode == StatusCode.INVALID_REFRESH_TOKEN || accessTokenApiCode == AUTHENTICATION_ERROR) || retryCount == NetworkConstants.MAXIMUM_API_RETRY_COUNT) {
//                        response.code
//                        return@addInterceptor response
//                    }
//                    if (accessToken.isNullOrBlank().not()) {
//                        // If updated token is not null, create new header and call the current api
//                        request = request.newBuilder()
//                            .header(
//                                NetworkConstants.AUTHORIZATION,
//                                NetworkConstants.BEARER + accessToken
//                            )
//                            .build()
//
//                        // retry the request
//                        response.close()
//                        response = chain.proceed(request)
//                    }
//                }
//                return@addInterceptor response
//            }
//            .build()
//    }
//}