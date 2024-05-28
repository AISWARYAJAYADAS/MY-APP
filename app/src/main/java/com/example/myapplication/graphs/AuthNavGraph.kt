package com.example.myapplication.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.myapplication.login.LoginScreen
import com.example.myapplication.splash.SplashScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.SPLASH.route,
    ) {
        composable(route = AuthScreen.SPLASH.route) {
             SplashScreen(navController = navController)
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