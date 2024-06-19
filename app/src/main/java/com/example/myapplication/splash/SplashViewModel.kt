package com.example.myapplication.splash

import androidx.lifecycle.ViewModel
import com.example.myapplication.api.RefreshTokenApiService
import com.example.myapplication.pref.SharedPref
import com.example.myapplication.utils.manager.ConfigurationManager
import com.example.myapplication.utils.manager.TokenExpiryManager
import com.google.firebase.installations.FirebaseInstallations
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val sharedPref: SharedPref,
    private val tokenExpiryManager: TokenExpiryManager,
    private val configurationManager: ConfigurationManager
) : ViewModel() {

    fun saveDeviceId() {
        FirebaseInstallations.getInstance().getToken(true).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                sharedPref.saveDeviceId(task.result?.token.toString())
            }
        }
    }

    fun isTokenAvailable(): Boolean {
        return tokenExpiryManager.isTokenAvailable()
    }

    fun fetchMasterConfiguration() {
      //  getUrlConfigurations()
        configurationManager.callMasterConfiguration()
    }
}

