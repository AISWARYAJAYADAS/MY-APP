package com.example.myapplication.graphs

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.myapplication.api.RefreshTokenApiService
import com.example.myapplication.login.LoginScreen
import com.example.myapplication.splash.SplashScreen
import com.example.myapplication.splash.SplashViewModel
import com.example.myapplication.utils.manager.TokenExpiryManager
import javax.inject.Inject

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.SPLASH.route,
    ) {
        composable(route = AuthScreen.SPLASH.route) {
            val splashViewModel: SplashViewModel = hiltViewModel()
            SplashScreen(
                navController = navController,
                splashViewModel = splashViewModel
            )
        }

        composable(route = AuthScreen.Login.route) {
            LoginScreen(navController = navController)
        }

        composable(route = AuthScreen.Forgot.route) {
            //  ForgotPasswordScreen(navController = navController)
        }
    }
}

sealed class AuthScreen(val route: String) {
    object SPLASH : AuthScreen(route = "SPLASH")
    object Login : AuthScreen(route = "LOGIN")
    object Forgot : AuthScreen(route = "FORGOT")
}