package com.example.myapplication.splash

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.BottomBarScreen
import com.example.myapplication.R
import com.example.myapplication.graphs.AuthScreen
import com.example.myapplication.graphs.Graph
import com.example.myapplication.login.LoginViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController

) {
    val splashViewModel = hiltViewModel<SplashViewModel>()

    LaunchedEffect(Unit){
        splashViewModel.saveDeviceId()
        delay(2000)

        navigateToNextScreen(splashViewModel,navController)


    }

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_splash),
            contentDescription = "",
            modifier = Modifier.size(200.dp)
        )

    }

}

/***
 * Method to navigate to next screen
 */
fun navigateToNextScreen(splashViewModel: SplashViewModel, navController: NavHostController) {
    Log.d("SplashViewModel", "getAuthToken: ${splashViewModel.getAuthToken()}")
    if (splashViewModel.getAuthToken().isBlank()) {
        navController.navigate(AuthScreen.Login.route) {
            popUpTo(AuthScreen.SPLASH.route) { inclusive = true }
        }
    } else {
        navController.navigate(Graph.MAIN_SCREEN_PAGE) {
            popUpTo(Graph.AUTHENTICATION) { inclusive = true }
        }
    }
}


@Preview
@Composable
private fun PreviewSplashScreen() {
    SplashScreen(
        navController = NavHostController(LocalContext.current)
    )

}