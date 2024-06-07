package com.example.myapplication.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GalleryFolderDropdownTextField(
    text: String,
    trailingIcon: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(text = text, fontSize = 14.sp)
        Spacer(modifier = Modifier.width(8.dp)) // Adjust spacing as needed
        trailingIcon()
    }
}