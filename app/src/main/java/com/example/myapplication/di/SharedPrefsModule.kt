package com.example.myapplication.di

import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication.pref.SharedPref
import com.example.myapplication.utils.PrefConstants.PREF
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPrefsModule {

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideSharedPrefs(preferences: SharedPreferences) = SharedPref(preferences)

    @Singleton
    @Provides
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver? =
        context.contentResolver
}