package com.example.myapplication.splash

import androidx.lifecycle.ViewModel
import com.example.myapplication.pref.SharedPref
import com.google.firebase.installations.FirebaseInstallations
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharedPref: SharedPref
) : ViewModel() {

    /**
     * Saves the device ID to shared preferences.
     * Uses FirebaseInstallations to get the device ID and saves it to shared preferences.
     */
    fun saveDeviceId() {
        FirebaseInstallations.getInstance().getToken(true).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                sharedPref.saveDeviceId(task.result?.token.toString())
            }
        }
    }


    /**
     * Function that returns the access token from shared preferences.
     * */
    fun getAuthToken(): String {
        return sharedPref.getAccessToken()
    }



}