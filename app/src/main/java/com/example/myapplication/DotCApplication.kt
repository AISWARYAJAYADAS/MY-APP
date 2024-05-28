package com.example.myapplication

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DotCApplication :Application() {

    companion object {
        lateinit var appContext: Context

        fun getString(id: Int): String {
            return appContext.getString(id)
        }

    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        //disable night mode
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}