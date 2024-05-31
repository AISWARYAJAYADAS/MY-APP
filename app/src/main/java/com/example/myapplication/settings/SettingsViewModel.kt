package com.example.myapplication.settings

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.myapplication.DotCApplication
import com.example.myapplication.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
): ViewModel() {

    fun getSettingsList(): List<SettingsOption> {
        return listOf(
            SettingsOption(
                SettingsItem.NOTIFICATION_SETTINGS,
                R.drawable.ic_notification_settings,
                DotCApplication.getString(R.string.notification_settings)
            ),
            SettingsOption(
                SettingsItem.PRIVACY_POLICY,
                R.drawable.ic_privacy_policy_settings,
                DotCApplication.getString(R.string.privacy_policy_settings)
            ),
            SettingsOption(
                SettingsItem.LOGOUT,
                R.drawable.ic_logout_settings,
                DotCApplication.getString(R.string.logout_settings)
            )
        )
    }

    enum class SettingsItem {
        NOTIFICATION_SETTINGS,
        PRIVACY_POLICY,
        LOGOUT
    }



}