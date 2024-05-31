package com.example.myapplication.profile

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.myapplication.post_listing.PostListingViewModel
import com.example.myapplication.profile.components.ProfileCard

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
    Log.d("ProfileScreen", "ProfileImageUrl: $profileImageUrl, Name: $name, LikeCount: $likeCount, DislikeCount: $dislikeCount")


    Column(
        modifier = Modifier
            .padding(top = 85.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
            .background(
                color = colorResource(id = R.color.app_background)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        ProfileCard(
            profileImageUrl = profileImageUrl,
            name = name,
            likeCount = likeCount,
            dislikeCount = dislikeCount,
            onEditProfileClick = {},
            onUpdateProfileImageClick = {},
            achievements = listOf(
                ProfileAchievements("出品数", "345"),
                ProfileAchievements("フォロワー", "22345"),
            ),
            snsIcons = listOf(
                ProfileSnsDetails(
                    id = 1,
                    icon = R.drawable.ic_profile_sns_sb,
                    title = "SBProfile",
                    url = ""
                ),
                ProfileSnsDetails(
                    id = 2,
                    icon = R.drawable.ic_profile_sns_insta,
                    title = "Instagram",
                    url = ""
                ),
                ProfileSnsDetails(
                    id = 3,
                    icon = R.drawable.ic_profile_sns_wear,
                    title = "Wear",
                    url = ""
                ),
                ProfileSnsDetails(
                    id = 4,
                    icon = R.drawable.ic_profile_sns_youtube,
                    title = "Youtube",
                    url = ""
                ),
                ProfileSnsDetails(
                    id = 5,
                    icon = R.drawable.ic_profile_sns_twitter,
                    title = "Twitter",
                    url = ""
                ),
                ProfileSnsDetails(
                    id = 6,
                    icon = R.drawable.ic_profile_sns_tiktok,
                    title = "Tiktok",
                    url = ""
                )


            )
        )

        Spacer(modifier = Modifier.height(24.dp))
        // ProfileTabLayout(postListingViewModel = viewModel, onTabSelected = {})

        val sampleTabs = listOf(
            TabData("出品中", 10),
            TabData("取引中", null),
            TabData("取引完了", 5)
        )

        var selectedTabIndex by remember { mutableIntStateOf(0) }

        CustomTabLayout(
            tabData = sampleTabs,
            selectedTabIndex = selectedTabIndex,
            onTabSelected = { index ->
                selectedTabIndex = index
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Conditionally display content based on selectedTabIndex
        when (selectedTabIndex) {
            0 ->
                LazyColumn {
                    items(5) { index ->
                        Text(
                            text = "First Tab data $index",
                            color = Color.Green,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

            1 ->

                LazyColumn {
                    items(5) { index ->
                        Text(
                            text = "Second tab data $index",
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }


            2 ->

                LazyColumn {
                    items(5) { index ->
                        Text(
                            text = "Third tab data $index",
                            color = Color.Black,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }


        }
    }

}

@Preview
@Composable
private fun PreviewProfileScreen() {
    ProfileScreen()
}