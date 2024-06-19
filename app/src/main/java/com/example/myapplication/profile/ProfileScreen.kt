package com.example.myapplication.profile

import CustomDropdown
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.components.CustomTabLayout
import com.example.myapplication.components.TabData
import com.example.myapplication.post_listing.PostListingScreen
import com.example.myapplication.post_listing.PostListingViewModel
import com.example.myapplication.profile.components.ProfileCard
import com.example.myapplication.ui.common.DropDownModel

@Composable
fun ProfileScreen(
    viewModel: PostListingViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = Unit) {
        viewModel.getUserDetails(forceUpdate = true)


    }

    val profileImageUrl by viewModel.profileImageUrl.collectAsState()
    val name by viewModel.name.collectAsState()
    val likeCount by viewModel.likeCount.collectAsState()
    val dislikeCount by viewModel.dislikeCount.collectAsState()


    // Log collected state values
    Log.d(
        "ProfileScreen",
        "ProfileImageUrl: $profileImageUrl, Name: $name, LikeCount: $likeCount, DislikeCount: $dislikeCount"
    )


    Column(
        modifier = Modifier
            .padding(top = 85.dp, start = 8.dp, end = 8.dp)
            .background(
                color = colorResource(id = R.color.app_background)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        //  Spacer(modifier = Modifier.height(8.dp))
        ProfileCard(
            profileImageUrl = profileImageUrl,
            name = name,
            likeCount = likeCount,
            dislikeCount = dislikeCount,
            onEditProfileClick = {},
            onUpdateProfileImageClick = {},
            achievements = viewModel.getProfileAchievements(),
            snsIcons = viewModel.getSnsDetails()
        )

        Spacer(modifier = Modifier.height(24.dp))
        // ProfileTabLayout(postListingViewModel = viewModel, onTabSelected = {})

//        SortDropdown(viewModel)
//        PostListScreen(viewModel)

        PostListingScreen()
    }


}

@Preview
@Composable
private fun PreviewProfileScreen() {
    ProfileScreen()
}