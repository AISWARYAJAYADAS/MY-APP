package com.example.myapplication.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.DotCApplication
import com.example.myapplication.R
import com.example.myapplication.api.model.Output
import com.example.myapplication.pref.SharedPref
import com.example.myapplication.utils.NetworkConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.example.myapplication.utils.StatusCode
import com.example.myapplication.utils.manager.ConfigurationManager
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val sharedPref: SharedPref,
    private val configurationManager: ConfigurationManager
) : ViewModel() {

    var username = mutableStateOf(TextFieldValue())
    var password = mutableStateOf(TextFieldValue())
    var usernameError = mutableStateOf<Int?>(null)
    var passwordError = mutableStateOf<Int?>(null)

    private val _loginState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Idle)
    var loginState: StateFlow<LoginState> = _loginState

    // Boolean flags to track error status
    var isUsernameError by mutableStateOf(false)
    var isPasswordError by mutableStateOf(false)

    fun validateInput() {

        if (username.value.text.isBlank()) {
            usernameError.value = R.string.mandatory_field
            isUsernameError = true
        } else {
            usernameError.value = null
            isUsernameError = false // Reset error status
        }
        if (password.value.text.isBlank()) {
            passwordError.value = R.string.mandatory_field
            isPasswordError = true
        } else {
            passwordError.value = null
            isPasswordError = false // Reset error status
        }
        if (username.value.text.isNotBlank() && password.value.text.isNotBlank()) {
            val loginRequest = LoginRequest(
                memberId = username.value.text ?: "",
                password = password.value.text ?: "",
                appType = NetworkConstants.ANDROID,
                deviceId = "123456789"
            )
            login(loginRequest)
        }
    }

    fun login(loginRequest: LoginRequest) = viewModelScope.launch {
        _loginState.value = LoginState.Loading(loading = true)
        repository.login(loginRequest = loginRequest).collect { response ->
            when (response) {
                is Output.Success -> {
                    _loginState.value = LoginState.Success(response.value?.data ?: Login())
                    val accessToken = response.value?.data?.accessToken
                    val refreshToken = response.value?.data?.refreshToken
                    accessToken?.let {
                        sharedPref.saveAccessToken(it)
                    }
                    refreshToken?.let {
                        sharedPref.saveRefreshToken(it)
                    }
                    fetchMasterConfiguration()
                    if (response.value?.data?.isProfileVerified == true)
                        sharedPref.saveInitialProfileUpdatedStatus(status = true)

                    Log.d("LoginViewModel", "Login successful - Access Token: ${response.value?.data?.accessToken}")
                    Log.d("LoginViewModel", "Login successful - Refresh Token: ${response.value?.data?.refreshToken}")
                    Log.d("LoginViewModel", "Login successful - Profile verified status: ${response.value?.data?.isProfileVerified}")
                }

                is Output.Error -> {
                    _loginState.value = LoginState.Loading(loading = false)
                    val errorMessage = when (response.apiError.statusCode) {
                        StatusCode.AUTHENTICATION_ERROR, StatusCode.T_ACCOUNT_LOGIN -> {
                            DotCApplication.appContext.getString(R.string.incorrect_id_pwd)
                        }

                        StatusCode.IN_ACTIVE_USER -> {
                            DotCApplication.appContext.getString(R.string.inactive_user)
                        }

                        else -> {
                            response.apiError ?: ""
                        }
                    }
                   // _loginState.value = com.example.myapplication.login.LoginState.Error(errorMessage)

                    _loginState.value = LoginState.Error(
                        message = (errorMessage ?: "").toString(),
                        errorCode = response.apiError.statusCode,
                        errorMessageFromApi = response.apiError.errorMessageFromApi
                    )
                }
            }
        }
    }


    private fun fetchMasterConfiguration() {
        if (sharedPref.isConfigUpdated().not()) {
            configurationManager.callMasterConfiguration()
        }
    }

}

sealed class LoginState {
    object Idle : LoginState()
    data class Success(val loginData: Login) : LoginState()

    data class Error(
        val message: String?,
        val errorCode: Int,
        val showError: Boolean = true,
        val errorMessageFromApi: String? = null
    ) : LoginState()

    data class Loading(val loading: Boolean, val showLoader: Boolean = true) : LoginState()
}