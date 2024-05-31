package com.example.myapplication.utils.manager

import com.example.myapplication.profile.model.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileManager @Inject constructor(){
    private val _currentUserProfile = MutableStateFlow<Profile?>(null)
    val currentUserProfile: StateFlow<Profile?> = _currentUserProfile.asStateFlow()

    fun setUserProfile(profile: Profile?) {
        _currentUserProfile.value = profile

    }
}