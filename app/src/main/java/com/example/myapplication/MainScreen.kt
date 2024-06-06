package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.components.CustomAppBar
import com.example.myapplication.create_listing.CreateListingBottomSheetContent
import com.example.myapplication.graphs.Graph
import com.example.myapplication.graphs.MainNavGraph


@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {

    Scaffold(
        topBar = {
            val currentBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = currentBackStackEntry?.destination
            if (currentDestination?.route?.startsWith(BottomBarScreen.Profile.route) == true) {
                CustomAppBar(
                    title = R.string.profile,
                    actions = {
                        IconButton(onClick = { /* Handle default icon click */ }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_notification_bel),
                                contentDescription = "Notification"
                            ) // Example icon
                        }

                        IconButton(onClick = {
                            navController.navigate(route = Graph.SETTINGS)
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_settings_profile),
                                contentDescription = "Settings"
                            ) // Example icon
                        }
                    }
                )
            }
        },
        bottomBar = {
            BottomBar(navController = navController)
        }) { padding ->
        Modifier
            .background(color = colorResource(id = R.color.app_background))
            .padding(padding)
        MainNavGraph(navController = navController)
    }
}


@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {

        Surface(
            color = Color.White, // Background color
            contentColor = Color.Black, // Content color
            shadowElevation = 8.dp
        ) {
            NavigationBar(
                containerColor = colorResource(id = R.color.bottom_nav_background)
            ) {
                screens.forEachIndexed { index, screen ->
                    AddItem(
                        screen = screen,
                        currentDestination = currentDestination,
                        navController = navController
                    )
                    if (index < screens.lastIndex) {
                        AddFab(navController = navController) // Add FAB between items except the last one
                    }
                }
            }
        }
    }
}


@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController,
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true


    NavigationBarItem(

        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                painter = painterResource(id = if (selected) screen.activeIcon else screen.inactiveIcon),
                contentDescription = "Navigation Icon"
            )
        },
        selected = selected,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = colorResource(id = R.color.bottom_nav_fab_icon),
            unselectedIconColor = colorResource(id = R.color.bottom_nav_un_elected_icon),
            indicatorColor = Color.White
        )
    )


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFab(navController: NavHostController) {
    val sheetState = rememberModalBottomSheetState() // sheet state - open or closed
    var showBottomSheet by remember { mutableStateOf(false) }

    // Add your FAB implementation here
    FloatingActionButton(
        shape = CircleShape,
        containerColor = colorResource(id = R.color.bottom_nav_fab_icon),
        onClick = {
            showBottomSheet = true
        },
        modifier = Modifier.size(50.dp)
        //.padding(start = 16.dp, end = 16.dp)
    ) {
        // Add FAB icon or content here
        Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White)
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            scrimColor = Color.Transparent,
            containerColor = Color.White,
            shape = RoundedCornerShape(
                topStart = 12.dp,
                topEnd = 12.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ),
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {

            CreateListingBottomSheetContent(navController = navController)

        }
    }
}


@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(navController = rememberNavController())
}