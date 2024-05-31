package com.example.myapplication.post_listing

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.api.model.Output
import com.example.myapplication.profile.ProfileRepository
import com.example.myapplication.profile.model.Profile
import com.example.myapplication.utils.INDEX
import com.example.myapplication.utils.manager.ProfileManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListingViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val profileManager: ProfileManager

) : ViewModel() {
    private val _profileDetails = MutableStateFlow<Profile?>(null)
    val profileDetails: StateFlow<Profile?> = _profileDetails
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _profileImageUrl = MutableStateFlow("")
    val profileImageUrl: StateFlow<String> = _profileImageUrl.asStateFlow()

    private val _likeCount = MutableStateFlow("")
    val likeCount: StateFlow<String> = _likeCount.asStateFlow()

    private val _dislikeCount = MutableStateFlow("")
    val dislikeCount: StateFlow<String> = _dislikeCount.asStateFlow()

//    init {
//        getUserDetails()
//    }

    fun getUserDetails(forceUpdate: Boolean = false) = viewModelScope.launch {
        Log.d("PostListingViewModel", "Fetching user details...")
        _profileDetails.value = profileManager.currentUserProfile.value
        if (profileManager.currentUserProfile.value == null || forceUpdate) {
            repository.getUserDetails().collect { response ->
                when (response) {
                    is Output.Success -> {
                        val profile = response.value?.data
                        Log.d("PostListingViewModel", "Profile data fetched: $profile")
                        profileManager.setUserProfile(profile)
                        profile?.let {
                            _name.value = it.nickname
                            _profileImageUrl.value = it.image.smallUrl
                            _likeCount.value = it.goodReviewCount.toString()
                            _dislikeCount.value = it.badReviewCount.toString()
                            Log.d("PostListingViewModel", "Name: ${it.nickname}, ImageUrl: ${it.image.smallUrl}")
                        }
                    }
                    is Output.Error -> {
                        // Handle error
                        Log.e("PostListingViewModel", "Error: ${response.apiError}")
                    }
                }
            }
        } else {
            profileManager.currentUserProfile.value?.let { profile ->
                _name.value = profile.nickname
                _profileImageUrl.value = profile.image.smallUrl
                _likeCount.value = profile.goodReviewCount.toString()
                _dislikeCount.value = profile.badReviewCount.toString()
            }
        }
    }



}