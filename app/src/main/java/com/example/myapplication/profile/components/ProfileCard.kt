package com.example.myapplication.profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import com.example.myapplication.profile.ProfileAchievements
import com.example.myapplication.profile.ProfileSnsDetails

@Composable
fun ProfileCard(
    profileImageUrl: String?,
    name: String,
    likeCount: String?,
    dislikeCount: String?,
    onEditProfileClick: () -> Unit,
    onUpdateProfileImageClick: () -> Unit,
    achievements: List<ProfileAchievements>,
    snsIcons: List<ProfileSnsDetails>
) {


    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth().padding(top = 24.dp)
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(4.dp),
    ) {
        // first row content
        Row(
            modifier = Modifier.padding(top = 24.dp, start = 12.dp)
        ) {

            // 1. Profile Image
            ProfileImage(
                profileImageUrl,
                onUpdateProfileImageClick
            )


            Spacer(modifier = Modifier.width(24.dp))


            // 2. profile name,like count,dislike count
            ProfileInfo(name, likeCount, dislikeCount)

        }


        // 2nd row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            EditProfileButton(onEditProfileClick)
        }


      //  Spacer(modifier = Modifier.height(24.dp))

        ProfileAchievementGrid(achievements = achievements)
        Spacer(modifier = Modifier.height(24.dp))

         ProfileSnsIconsGrid(snsIcons = snsIcons)
        Spacer(modifier = Modifier.height(24.dp))

    }

}

@Composable
fun ProfileSnsIconsGrid(snsIcons:List<ProfileSnsDetails>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(6), // Adjust columns as needed
        modifier = Modifier.fillMaxWidth()
    ) {
        items(snsIcons) { item ->
            ProfileSnsIcon(item)
        }
    }

}

@Composable
fun ProfileSnsIcon(item: ProfileSnsDetails) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(item.icon),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp))
    }

}


@Composable
fun ProfileImage(profileImageUrl: String?, onUpdateProfileImageClick: () -> Unit) {
    Box {
        Image(
            painter = if (profileImageUrl.isNullOrEmpty()) {
                painterResource(R.drawable.ic_profile_image_placeholder)
            } else {
                // Use coil for asynchronous image loading
                rememberAsyncImagePainter(profileImageUrl)
            },
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .align(Alignment.Center)
        )
        IconButton(
            onClick = onUpdateProfileImageClick,
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.BottomEnd)
                .padding(bottom = 4.dp, end = 4.dp)

//            modifier = Modifier
//                .size(20.dp)
//                .align(Alignment.BottomEnd) // Align to bottom-end of Box
//                //  .offset(x = (5).dp, y = (-5).dp)
//                .padding(bottom = 4.dp, end = 4.dp) // Padding for icon content
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_edit_profile_info_white),
                contentDescription = "Edit Profile",
                tint = Color.White,
                modifier = Modifier
                    .clip(CircleShape)// Optional: Remove default button background
                    .background(Color(0xFF2BB0E8))

            )
        }

    }
}


@Composable
fun ProfileInfo(name: String, likeCount: String?, dislikeCount: String?) {

    Column {
        Text(
            text = name,
            fontSize = 17.sp
        )
        Spacer(modifier = Modifier.size(10.dp))

        Row {
            Icon(
                painter = painterResource(R.drawable.ic_like_emoji),
                tint = Color(0xFF54CC7C),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = likeCount ?: "0")
            Spacer(modifier = Modifier.width(18.dp))
            Icon(
                painter = painterResource(R.drawable.ic_dislike_emoji),
                tint = Color.Red,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = dislikeCount ?: "0")
        }

    }

}

@Composable
fun EditProfileButton(onEditProfileClick: () -> Unit) {
    Button(
        modifier = Modifier.padding(
            start = 24.dp,
            end = 24.dp,
            top = 8.dp,
            bottom = 8.dp
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color(
                0xFF2BB0E8
            )
        ),
        onClick = { onEditProfileClick.invoke() }
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_edit_profile_info_white),
            tint = Color.White,
            contentDescription = "",
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = stringResource(id = R.string.edit_profile_details),
            color = Color.White,
            fontSize = 13.sp
        )

    }


}

@Composable
fun ProfileAchievementGrid(achievements: List<ProfileAchievements>) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            // .fillMaxWidth()
            .padding(top = 24.dp),
        content = {
            items(achievements) { achievement ->
                ProfileAchievementItem(achievement)
            }
        }
    )
}

@Composable
fun ProfileAchievementItem(achievement: ProfileAchievements) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = achievement.label, fontSize = 14.sp, color = Color(0xFF949494))
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = achievement.value.toString(),
            fontSize = 20.sp,
            color = Color(0xFF212121),
            fontWeight = FontWeight.Bold
        )
    }

}

@Preview
@Composable
private fun PreviewProfileImage() {
    ProfileImage(
        profileImageUrl = "",
        onUpdateProfileImageClick = {}
    )
}


@Preview
@Composable
private fun PreviewProfileCard() {
    ProfileCard(
        profileImageUrl = "",
        name = "真由美",
        likeCount = "32",
        dislikeCount = "32",
        onEditProfileClick = {},
        onUpdateProfileImageClick = {},
        achievements = listOf(
            ProfileAchievements("出品数", 345),
            ProfileAchievements("フォロワー", 22345),
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
}