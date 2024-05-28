package com.example.myapplication.settings

import androidx.annotation.Keep

/**
 * Model class to hold settings options.
 * */
@Keep
data class SettingsOption(val type: SettingsViewModel.SettingsItem, val icon: Int, val option: String)