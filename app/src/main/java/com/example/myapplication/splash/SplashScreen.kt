package com.example.myapplication.splash

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.BottomBarScreen
import com.example.myapplication.R
import com.example.myapplication.api.RefreshTokenApiService
import com.example.myapplication.graphs.AuthScreen
import com.example.myapplication.graphs.Graph
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        splashViewModel.saveDeviceId()

        delay(2000) // Simulate a delay for the splash screen

        val isTokenAvailable = splashViewModel.isTokenAvailable()
        if (isTokenAvailable) {
            navController.navigate(Graph.MAIN_SCREEN_PAGE) {
                popUpTo(Graph.AUTHENTICATION) { inclusive = true }
            }
        } else {
            navController.navigate(AuthScreen.Login.route) {
                popUpTo(Graph.AUTHENTICATION) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
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









//import android.util.Log
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.size
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import com.example.myapplication.BottomBarScreen
//import com.example.myapplication.R
//import com.example.myapplication.api.RefreshTokenApiService
//import com.example.myapplication.graphs.AuthScreen
//import com.example.myapplication.graphs.Graph
//import com.example.myapplication.login.LoginViewModel
//import com.example.myapplication.utils.manager.TokenExpiryManager
//import kotlinx.coroutines.delay




//@Composable
//fun SplashScreen(
//    navController: NavHostController,
//    splashViewModel: SplashViewModel = hiltViewModel()
//) {
//    val isLoggedIn = splashViewModel.isLoggedIn.collectAsState(initial = false)
//
//
//    LaunchedEffect(Unit) {
//     //   splashViewModel.fetchMasterConfiguration()
//        splashViewModel.saveDeviceId()
//
//        delay(2000) // Simulate a delay for the splash screen
//
//        val isTokenAvailable = splashViewModel.isTokenAvailable()
//        if (isTokenAvailable) {
//            navController.navigate(Graph.MAIN_SCREEN_PAGE) {
//                popUpTo(Graph.AUTHENTICATION) { inclusive = true }
//            }
//        } else {
//            navController.navigate(AuthScreen.Login.route) {
//                popUpTo(Graph.AUTHENTICATION) { inclusive = true }
//            }
//        }
//    }
//
//
////    LaunchedEffect(key1 = true) {
////        // Check if token is available
////        if (tokenExpiryManager.isTokenAvailable()) {
////            // If token is available, try to refresh it
////            tokenExpiryManager.refreshToken(refreshTokenApi)
////        }
////        // Navigate based on token availability
////        if (tokenExpiryManager.isTokenAvailable()) {
////            navController.navigate(Graph.MAIN_SCREEN_PAGE)
////        } else {
////            navController.navigate(AuthScreen.Login.route)
////        }
////    }
//
////    LaunchedEffect(Unit) {
////        Log.d("SplashScreen", "AuthToken on startup: ${splashViewModel.getAuthToken()}")
////        splashViewModel.saveDeviceId()
////        delay(2000)
////        Log.d("SplashScreen", "AuthToken after delay: ${splashViewModel.getAuthToken()}")
////
////        navigateToNextScreen(splashViewModel, navController)
////    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//
//        Image(
//            painter = painterResource(id = R.drawable.ic_splash),
//            contentDescription = "",
//            modifier = Modifier.size(200.dp)
//        )
//
//    }
//
//}
//
//
///***
// * Method to navigate to next screen
// */
//fun navigateToNextScreen(splashViewModel: SplashViewModel, navController: NavHostController) {
//    Log.d("SplashViewModel", "getAuthToken: ${splashViewModel.getAuthToken()}")
//    if (splashViewModel.getAuthToken().isBlank()) {
//        navController.navigate(AuthScreen.Login.route) {
//            popUpTo(AuthScreen.SPLASH.route) { inclusive = true }
//        }
//    } else {
//        navController.navigate(Graph.MAIN_SCREEN_PAGE) {
//            popUpTo(Graph.AUTHENTICATION) { inclusive = true }
//        }
//    }
//}


//@Preview
//@Composable
//private fun PreviewSplashScreen() {
//    SplashScreen(
//        navController = NavHostController(LocalContext.current),
//        tokenExpiryManager = TokenExpiryManager()
//    )
//
//}