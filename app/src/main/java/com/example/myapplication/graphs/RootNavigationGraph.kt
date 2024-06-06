package com.example.myapplication.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.MainScreen
import com.example.myapplication.settings.SettingsScreen

@Composable
fun RootNavigationGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION) {

        authNavGraph(navController = navController)

        composable(route = Graph.MAIN_SCREEN_PAGE) {
            MainScreen()
        }


        
    }


}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val MAIN_SCREEN_PAGE = "main_screen_graph"
    const val SETTINGS = "settings_graph"
    const val DETAILS = "details_graph"
    const val CREATE_LISTING = "create_listing_graph"
}