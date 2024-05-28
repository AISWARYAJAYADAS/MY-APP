package com.example.myapplication.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.api.model.Output
import com.example.myapplication.login.LoginState
import com.example.myapplication.pref.SharedPref
import com.google.errorprone.annotations.Keep
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val sharedPref: SharedPref
) : ViewModel() {
    private val _logoutState: MutableStateFlow<LogoutViewState> =
        MutableStateFlow(LogoutViewState.Loading(false))
    var logoutState: StateFlow<LogoutViewState> = _logoutState

    fun clearSharePrefValues() {
        sharedPref.clear()
    }

    fun logout() = viewModelScope.launch {
        _logoutState.value = LogoutViewState.Loading(loading = true)
        repository.logout().collect { response ->

            if (response is Output.Success || response is Output.Error)
                logoutUser()

        }
    }

    private fun logoutUser() {
        _logoutState.value = LogoutViewState.Loading(loading = false)
        _logoutState.value = LogoutViewState.LogoutSuccess
    }

    fun removeDevice() = viewModelScope.launch {
        val deviceId = sharedPref.getDeviceId()
        repository.removeDevice(deviceId).collect {
            _logoutState.value = LogoutViewState.DeviceDeletionComplete
            //setting the value to false since user will be logged out
            sharedPref.saveIsFCMTokenAdded(false)
        }
    }

}


@Keep
sealed class LogoutViewState {
    object LogoutSuccess : LogoutViewState()

    data class Error(val message: String?) :
        LogoutViewState()

    data class Loading(val loading: Boolean) : LogoutViewState()

    object DeviceDeletionComplete : LogoutViewState()
}