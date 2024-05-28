package com.example.myapplication.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.myapplication.MainViewModel
import com.example.myapplication.login.LoginViewModel

@Composable
fun HomeScreen(

) {
    val viewModel = hiltViewModel<MainViewModel>()

    LaunchedEffect(Unit) {
        viewModel.addDevice()
    }

    Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "HOME SCREEEN")
    }



}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}