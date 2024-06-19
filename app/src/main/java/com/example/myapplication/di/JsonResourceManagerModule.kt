package com.example.myapplication.di

import android.content.Context
import android.content.res.Resources
import com.example.myapplication.utils.manager.JsonResourceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JsonResourceManagerModule {

    @Singleton
    @Provides
    fun provideResources(@ApplicationContext context: Context): Resources? = context.resources

    @Singleton
    @Provides
    fun provideJsonManager(resources: Resources?) = JsonResourceManager(resources)

}