package com.example.myapplication.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(
    title: Int,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (() -> Unit)? = null,
    colors: Color? = colorResource(id = R.color.app_background),
) {
    colors?.let {
        TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = it
        )
    }?.let {
        CenterAlignedTopAppBar(
        colors = it,
        title = {
            Text(
                maxLines = 1,
                text = stringResource(id = title),
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.font_medium))
            )
        },

        actions = {
            if (actions != null) {
                actions()
            }
        },

        navigationIcon = {
            if (navigationIcon != null) {
                navigationIcon()
            }
        }


    )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PreviewCustomAppBar() {
    CustomAppBar(
        title = R.string.profile,
        actions = {
            IconButton(onClick = { /* Handle default icon click */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_notification_bel),
                    contentDescription = "Notification"
                ) // Example icon
            }

            IconButton(onClick = { /* Handle default icon click */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_settings_profile),
                    contentDescription = "Settings"
                ) // Example icon
            }
        }
    )

}