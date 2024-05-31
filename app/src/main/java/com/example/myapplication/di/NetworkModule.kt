package com.example.myapplication.di


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
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
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

