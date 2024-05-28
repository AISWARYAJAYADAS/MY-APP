package com.example.myapplication


import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    @DrawableRes val activeIcon: Int,
    @DrawableRes val inactiveIcon: Int
) {
    object Home : BottomBarScreen(
        route = "HOME",
        title = "ホーム",
        activeIcon = R.drawable.ic_home_active,
        inactiveIcon = R.drawable.ic_home_inactive
    )

    object Profile : BottomBarScreen(
        route = "PROFILE",
        title = "プロフィール",
        activeIcon = R.drawable.ic_profile_active,
        inactiveIcon = R.drawable.ic_profile_inactive
    )

}