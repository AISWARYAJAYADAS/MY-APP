package com.example.myapplication.create_listing

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CreateListingScreen() {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        
        Text(text = "Create Listing")

    }
}