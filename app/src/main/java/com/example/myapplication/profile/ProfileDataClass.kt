package com.example.myapplication.profile

import android.os.Parcelable
import androidx.annotation.Keep

/**
 * Data class representing achievements in a user's profile
 *
 * @param label the label of the achievement
 * @param value the value of the achievement
 */
@Keep
data class ProfileAchievements(
    val label: String,
    val value: String,
)

/**
 * Data class representing social media details for a user's profile
 *
 * @param id the ID of the social media account
 * @param title the title of the social media account
 * @param icon the icon of the social media account
 * @param url the URL of the social media account
 */

@Keep
data class ProfileSnsDetails(
    val id: Int,
    val title: String,
    val icon: Int,
    val url: String,
)