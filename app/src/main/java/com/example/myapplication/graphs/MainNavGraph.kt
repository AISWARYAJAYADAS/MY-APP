package com.example.myapplication.graphs

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.myapplication.BottomBarScreen
import com.example.myapplication.MainViewModel
import com.example.myapplication.ScreenContent
import com.example.myapplication.create_listing.CreateListingScreen
import com.example.myapplication.gallery.GalleryPickerScreen
import com.example.myapplication.home.HomeScreen
import com.example.myapplication.post_listing.PostListingScreen
import com.example.myapplication.profile.ProfileScreen
import com.example.myapplication.settings.SettingsScreen


@Composable
fun MainNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        route = Graph.MAIN_SCREEN_PAGE,
        startDestination = BottomBarScreen.Profile.route
    ) {

        composable(route = BottomBarScreen.Home.route) {
         HomeScreen()
        }

        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen()
        }

        settingsNavGraph(navController = navController)
        detailsNavGraph(navController = navController)
        createListingNavGraph(navController)


    }

}


fun NavGraphBuilder.settingsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.SETTINGS,
        startDestination = SettingsScreen.SETTINGS.route
    ) {
        composable(route = SettingsScreen.SETTINGS.route) {
            SettingsScreen(navController = navController)
        }
//        composable(route = DetailsScreen.BTM_SUB_DETAILS_PAGE.route) {
//            ScreenContent(name = "Sub Detail Page") {}
//        }
    }
}




fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.BTM_DETAIL_PAGE.route
    ) {
        composable(route = DetailsScreen.BTM_DETAIL_PAGE.route) {
            ScreenContent(name = "Detail Page") {
                navController.navigate(DetailsScreen.BTM_SUB_DETAILS_PAGE.route)
            }
        }
        composable(route = DetailsScreen.BTM_SUB_DETAILS_PAGE.route) {
            ScreenContent(name = "Sub Detail Page") {}
        }
    }
}




sealed class SettingsScreen(val route: String) {
    object SETTINGS : SettingsScreen(route = "SETTINGS_PAGE")
}

sealed class DetailsScreen(val route: String) {
    object BTM_DETAIL_PAGE : DetailsScreen(route = "DETAIL_PAGE_")
    object BTM_SUB_DETAILS_PAGE : DetailsScreen(route = "DETAIL_PAGE_SUB")
}



fun NavGraphBuilder.createListingNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.CREATE_LISTING,
        startDestination = CreateListingRoutes.GalleryPickerScreen.route
    ) {

        composable(route = CreateListingRoutes.GalleryPickerScreen.route) {
            GalleryPickerScreen(navHostController = navController)
        }

        composable(route = CreateListingRoutes.ListNewItemScreen.route) {
            CreateListingScreen(
            )
        }
        composable(route = CreateListingRoutes.PostListingScreen.route){
            PostListingScreen()
        }


//        composable(route = CreateListingRoutes.ListNewItemScreen.route) {
//            // ListNewItemScreen(navController = navController)
//        }
//        composable(route = CreateListingRoutes.ListFromDraftScreen.route) {
//            // ListFromDraftScreen(navController = navController)
//        }
//
//        composable(route = CreateListingRoutes.ListingImageFullScreenPreview.route) {
//            // ListingImageFullScreenPreview(navController = navController)
//        }
//        composable(route = CreateListingRoutes.ImageCroppingScreen.route) {
//            // ImageCroppingScreen(navController = navController)
//        }
//        composable(route = CreateListingRoutes.CameraCaptureScreen.route) {
//            // CameraCaptureScreen(navController = navController)
//        }
//        composable(route = CreateListingRoutes.CameraCaptureImagePreview.route) {
//            // CameraCaptureImagePreview(navController = navController)
//        }
//        composable(route = CreateListingRoutes.DraftListingScreen.route) {
//            // DraftListingScreen(navController = navController)
//        }
    }
}


sealed class CreateListingRoutes(val route: String) {
    object GalleryPickerScreen : CreateListingRoutes("GALLERY_PICKER")
    object ListNewItemScreen : CreateListingRoutes("LIST_NEW_ITEM")
    object PostListingScreen : CreateListingRoutes("POST_LISTING")
    object ListFromDraftScreen : CreateListingRoutes("LIST_FROM_DRAFT")

    object ListingImageFullScreenPreview : CreateListingRoutes("IMAGE_FULL_SCREEN_PREVIEW")
    object ImageCroppingScreen : CreateListingRoutes("IMAGE_CROPPING")
    object CameraCaptureScreen : CreateListingRoutes("CAMERA_CAPTURE")
    object CameraCaptureImagePreview : CreateListingRoutes("CAMERA_CAPTURE_IMAGE_PREVIEW")
    object DraftListingScreen : CreateListingRoutes("DRAFT_LISTING")
}