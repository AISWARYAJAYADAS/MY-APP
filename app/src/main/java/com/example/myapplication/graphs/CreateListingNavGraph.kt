package com.example.myapplication.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.myapplication.login.LoginScreen

fun NavGraphBuilder.createListingNavGraph(navHostController: NavHostController) {
    composable(route = CreateListingRoutes.ListNewItemScreen.route) {
        //ListNewItemScreen(navController = navController)
    }
    composable(route = CreateListingRoutes.ListFromDraftScreen.route) {
        //ListNewItemScreen(navController = navController)
    }
}

sealed class CreateListingRoutes(val route: String) {
    object ListNewItemScreen : CreateListingRoutes(route = "LIST_NEW_ITEM")
    object ListFromDraftScreen : CreateListingRoutes(route = "LIST_FROM_DRAFT")

}