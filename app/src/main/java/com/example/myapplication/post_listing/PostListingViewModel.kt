package com.example.myapplication.post_listing

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication.DotCApplication
import com.example.myapplication.R
import com.example.myapplication.api.model.Output
import com.example.myapplication.profile.ProfileAchievements
import com.example.myapplication.profile.ProfileRepository
import com.example.myapplication.profile.ProfileSnsDetails
import com.example.myapplication.profile.model.Profile
import com.example.myapplication.ui.common.DropDownModel
import com.example.myapplication.utils.AppConstants
import com.example.myapplication.utils.INDEX
import com.example.myapplication.utils.ListingSortType
import com.example.myapplication.utils.ListingStatus
import com.example.myapplication.utils.manager.ProfileManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListingViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val profileManager: ProfileManager,
    private val postListingRepository: PostListingRepository

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
                            _profileDetails.value = it
                            Log.d(
                                "PostListingViewModel",
                                "Name: ${it.nickname}, ImageUrl: ${it.image.smallUrl}"
                            )
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
                _profileDetails.value = profile
            }
        }
    }


    fun getProfileAchievements(): ArrayList<ProfileAchievements> {
        val list = ArrayList<ProfileAchievements>()
        list.add(
            ProfileAchievements(
                label = DotCApplication.getString(R.string.entries),
                value = profileDetails.value?.postCount ?: 0
            )
        )

        list.add(
            ProfileAchievements(
                label = DotCApplication.getString(R.string.followers),
                value = profileDetails.value?.followerCount ?: 0
            )
        )
        Log.d("PostListingViewModel - ProfileAchievements", "list: $list")

        return list

    }


    fun getSnsDetails(): ArrayList<ProfileSnsDetails> {
        val snsDetailsList = ArrayList<ProfileSnsDetails>()

        val sbProfileEndPoint = profileDetails.value?.snsDetails?.dotstUrl
        val instagramEndPoint = profileDetails.value?.snsDetails?.instagramUrl
        val wearEndPoint = profileDetails.value?.snsDetails?.wearUrl
        val youtubeEndPoint = profileDetails.value?.snsDetails?.youtubeUrl
        val twitterEndPoint = profileDetails.value?.snsDetails?.twitterUrl
        val tiktokEndPoint = profileDetails.value?.snsDetails?.tiktokUrl

        if (!sbProfileEndPoint.isNullOrBlank()) {
            snsDetailsList.add(
                ProfileSnsDetails(
                    id = 1,
                    icon = R.drawable.ic_profile_sns_sb,
                    title = "SBProfile",
                    url = sbProfileEndPoint
                )
            )
        }

        if (!instagramEndPoint.isNullOrBlank()) {
            snsDetailsList.add(
                ProfileSnsDetails(
                    id = 2,
                    icon = R.drawable.ic_profile_sns_insta,
                    title = "Instagram",
                    url = "https://www.instagram.com/$instagramEndPoint"
                )
            )
        }

        if (wearEndPoint?.isNotBlank() == true) {
            snsDetailsList.add(
                ProfileSnsDetails(
                    id = 3,
                    icon = R.drawable.ic_profile_sns_wear,
                    title = "Wear",
                    url = "https://www.wear.jp/sp/$wearEndPoint/"
                )
            )
        }

        if (!youtubeEndPoint.isNullOrBlank()) {
            snsDetailsList.add(
                ProfileSnsDetails(
                    id = 4,
                    icon = R.drawable.ic_profile_sns_youtube,
                    title = "Youtube",
                    url = "https://www.youtube.com/channel/$youtubeEndPoint"
                )
            )
        }

        if (!twitterEndPoint.isNullOrBlank()) {
            snsDetailsList.add(
                ProfileSnsDetails(
                    id = 5,
                    icon = R.drawable.ic_profile_sns_twitter,
                    title = "Twitter",
                    url = "https://www.twitter.com/$twitterEndPoint"
                )
            )
        }

        if (!tiktokEndPoint.isNullOrBlank()) {
            snsDetailsList.add(
                ProfileSnsDetails(
                    id = 6,
                    icon = R.drawable.ic_profile_sns_tiktok,
                    title = "Tiktok",
                    url = "https://www.tiktok.com/$tiktokEndPoint"
                )
            )
        }

        return snsDetailsList
    }


//    var selectedTabPosition by mutableIntStateOf(0)
//        private set
//
//    var publishedPostsFlow: Flow<PagingData<Post>>? = null
//    var orderPlacedPostsFlow: Flow<PagingData<Post>>? = null
//    var orderCompletedPostsFlow: Flow<PagingData<Post>>? = null
//
//    fun onTabSelected(tabIndex: Int) {
//        selectedTabPosition = tabIndex
//    }
//
//    fun getPublishedListings(): Flow<PagingData<Post>> {
//        return publishedPostsFlow ?: postListingRepository.getPostListing(
//            limit = AppConstants.API_LISTING_LIMIT,
//            postStatus = ListingStatus.PUBLISHED,
//            sortBy = ListingSortType.NEWEST
//        ).cachedIn(viewModelScope).also {
//            publishedPostsFlow = it
//        }
//    }
//
//    fun getOrderPlacedListings(): Flow<PagingData<Post>> {
//        return orderPlacedPostsFlow ?: postListingRepository.getPostListing(
//            limit = AppConstants.API_LISTING_LIMIT,
//            postStatus = ListingStatus.ORDER_PLACED,
//            sortBy = ListingSortType.NEWEST
//        ).cachedIn(viewModelScope).also {
//            orderPlacedPostsFlow = it
//        }
//    }
//
//    fun getOrderCompletedListings(): Flow<PagingData<Post>> {
//        return orderCompletedPostsFlow ?: postListingRepository.getPostListing(
//            limit = AppConstants.API_LISTING_LIMIT,
//            postStatus = ListingStatus.ORDER_COMPLETED,
//            sortBy = ListingSortType.NEWEST
//        ).cachedIn(viewModelScope).also {
//            orderCompletedPostsFlow = it
//        }
//    }



    var selectedTabPosition by mutableIntStateOf(0)
        private set

    private val _selectedSortType = MutableStateFlow(ListingSortType.NEWEST)
    val selectedSortType: StateFlow<String> get() = _selectedSortType

    var publishedPostsFlow: Flow<PagingData<Post>>? = null
    var orderPlacedPostsFlow: Flow<PagingData<Post>>? = null
    var orderCompletedPostsFlow: Flow<PagingData<Post>>? = null





    fun onTabSelected(tabIndex: Int) {
        selectedTabPosition = tabIndex
    }

    fun onSortTypeSelected(sortType: String) {
        _selectedSortType.value = sortType
        // Reset flows to trigger new data load with selected sort type
        publishedPostsFlow = null
        orderPlacedPostsFlow = null
        orderCompletedPostsFlow = null
    }

    fun getPublishedListings(): Flow<PagingData<Post>> {
        return publishedPostsFlow ?: postListingRepository.getPostListing(
            limit = AppConstants.API_LISTING_LIMIT,
            postStatus = ListingStatus.PUBLISHED,
            sortBy = selectedSortType.value
        ).cachedIn(viewModelScope).also {
            publishedPostsFlow = it
        }
    }

    fun getOrderPlacedListings(): Flow<PagingData<Post>> {
        return orderPlacedPostsFlow ?: postListingRepository.getPostListing(
            limit = AppConstants.API_LISTING_LIMIT,
            postStatus = ListingStatus.ORDER_PLACED,
            sortBy = selectedSortType.value
        ).cachedIn(viewModelScope).also {
            orderPlacedPostsFlow = it
        }
    }

    fun getOrderCompletedListings(): Flow<PagingData<Post>> {
        return orderCompletedPostsFlow ?: postListingRepository.getPostListing(
            limit = AppConstants.API_LISTING_LIMIT,
            postStatus = ListingStatus.ORDER_COMPLETED,
            sortBy = selectedSortType.value
        ).cachedIn(viewModelScope).also {
            orderCompletedPostsFlow = it
        }
    }


}