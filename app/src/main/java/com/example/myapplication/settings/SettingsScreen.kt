package com.example.myapplication.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.components.CustomAlertDialog
import com.example.myapplication.components.CustomAppBar
import com.example.myapplication.graphs.Graph
import com.example.myapplication.profile.LogoutViewModel
import com.example.myapplication.profile.LogoutViewState
import com.example.myapplication.settings.SettingsViewModel

@Composable
fun SettingsScreen(
    navController: NavHostController,
) {
    val viewModel = hiltViewModel<LogoutViewModel>()
    val settingsViewModel = hiltViewModel<SettingsViewModel>()
    val logoutState by viewModel.logoutState.collectAsState()
    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }
    var showToast by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CustomAppBar(
                colors = Color.White,
                title = R.string.def_settings,
                actions = {},
                navigationIcon = {
                    Row {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }) {
                            Icon(
                                modifier = Modifier.size(17.dp),
                                painter = painterResource(id = R.drawable.ic_prev),
                                contentDescription = "Notification"
                            ) // Example icon
                        }
                        Text(
                            modifier = Modifier.padding(top = 14.dp),
                            text = stringResource(R.string.back)
                        )
                    }
                },
            )
        }
    ) {

        LazyColumn(
            modifier = Modifier
                .padding(it)
        ) {
            items(settingsViewModel.getSettingsList()) { settings ->
                ListItemSettings(text = settings.option, icon = settings.icon, onListClick = {
                    when (settings.type) {
                        SettingsViewModel.SettingsItem.LOGOUT -> {
                            showDialog = true
                        }
                        else -> {
                            // Handle other settings items here
                        }
                    }
                })
            }
        }
    }

    if (showDialog) {
        CustomAlertDialog(
            message = stringResource(R.string.logout_confirmation),
            noButtonText = stringResource(id = R.string.no),
            yesButtonText = stringResource(id = R.string.yes)
        ) {
            if (it) {
                viewModel.removeDevice()
            }
            showDialog = false
        }
    }

    // Observing Login State
    when (logoutState) {
        is LogoutViewState.Loading -> {
            // Show a loading indicator if needed
            // CircularProgressIndicator()
        }
        is LogoutViewState.Error -> {
            val eMsg = (logoutState as LogoutViewState.Error).message
            Toast.makeText(context, eMsg ?: "", Toast.LENGTH_SHORT).show()
            viewModel.resetLogoutState()
        }
        is LogoutViewState.LogoutSuccess -> {
            if (!showToast) {
                showToast = true
                Toast.makeText(context, "Log out successful", Toast.LENGTH_SHORT).show()
                viewModel.clearSharePrefValues()
                navController.navigate(Graph.AUTHENTICATION) {
                    popUpTo(Graph.ROOT) { inclusive = true }
                }
                showToast = false
                viewModel.resetLogoutState()
            }
        }
        is LogoutViewState.DeviceDeletionComplete -> {
            viewModel.logout()
        }
        else -> Unit
    }
}

@Composable
fun ListItemSettings(text: String, icon: Int, onListClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .wrapContentHeight()
            .clickable {
                onListClick.invoke()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentHeight(),
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "settings"
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(text = text, fontSize = 12.sp)
        }
        Divider(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewSettings() {
    SettingsScreen(navController = rememberNavController())
}



//package com.example.myapplication.settings
//
//import android.util.Log
//import android.widget.Toast
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.layout.wrapContentHeight
//import androidx.compose.foundation.layout.wrapContentSize
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.Button
//import androidx.compose.material3.Card
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Divider
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBarColors
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.rememberNavController
//import com.example.myapplication.DotCApplication.Companion.getString
//import com.example.myapplication.MainViewModel
//import com.example.myapplication.R
//import com.example.myapplication.components.CustomAlertDialog
//import com.example.myapplication.components.CustomAppBar
//import com.example.myapplication.graphs.AuthScreen
//import com.example.myapplication.graphs.Graph
//import com.example.myapplication.login.LoginState
//import com.example.myapplication.login.LoginViewModel
//import com.example.myapplication.profile.LogoutViewModel
//import com.example.myapplication.profile.LogoutViewState
//import com.example.myapplication.utils.LogoutFlow
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SettingsScreen(
//    navController: NavHostController,
//) {
//    val viewModel = hiltViewModel<LogoutViewModel>()
//    val settingsViewModel = hiltViewModel<SettingsViewModel>()
//    val logoutState by viewModel.logoutState.collectAsState()
//    val context = LocalContext.current
//
//    var showDialog by remember { mutableStateOf(false) }
//
//
//    Scaffold(
//        topBar = {
//            CustomAppBar(
//                colors = Color.White,
//                title = R.string.def_settings,
//                actions = {},
//                navigationIcon = {
//                    Row {
//                        IconButton(
//                            onClick = {
//                                navController.popBackStack()
//                            }) {
//                            Icon(
//                                modifier = Modifier.size(17.dp),
//                                painter = painterResource(id = R.drawable.ic_prev),
//                                contentDescription = "Notification"
//                            ) // Example icon
//                        }
//                        Text(
//                            modifier = Modifier.padding(top = 14.dp),
//                            text = getString(R.string.back)
//                        )
//                    }
//                },
//            )
//        }
//    ) {
//
//        LazyColumn(
//            modifier = Modifier
//                .padding(it)
//        ) {
//            items(settingsViewModel.getSettingsList()) { settings ->
//                ListItemSettings(text = settings.option, icon = settings.icon, onListClick = {
//                    when (settings.type) {
//                        SettingsViewModel.SettingsItem.LOGOUT -> {
//                            showDialog = true
//                        }
//
//                        else -> {
//                            // Handle other settings items here
//                        }
//                    }
//                })
//
//            }
//
//        }
//    }
//
//    if (showDialog) {
//        CustomAlertDialog(
//            message = stringResource(R.string.logout_confirmation),
//            noButtonText = stringResource(id = R.string.no),
//            yesButtonText = stringResource(id = R.string.yes)
//        ) {
//            if (it) {
//                //viewModel.logout() // Trigger logout action
//                 viewModel.removeDevice()
//            }
//            showDialog = false
//        }
//    }
//
//
//    var isLoading by remember { mutableStateOf(false) }
//    val errorMessage by remember { mutableStateOf<String?>(null) }
//
//    var showToast by remember { mutableStateOf(false) }
//
//
//    // Observing Login State
//    when (logoutState) {
//        is LogoutViewState.Loading -> {
//            // Show a loading indicator if needed
//            // CircularProgressIndicator()
//        }
//
//        is LogoutViewState.Error -> {
//            val eMsg = (logoutState as LogoutViewState.Error).message
//            Toast.makeText(context, eMsg ?: "", Toast.LENGTH_SHORT).show()
//        }
//
//        is LogoutViewState.LogoutSuccess  -> {
//            if (!showToast) {
//                showToast = true
//                Toast.makeText(context, "Log out successful", Toast.LENGTH_SHORT).show()
//                viewModel.clearSharePrefValues()
//                Log.d("Logout", "Token after logout: ${viewModel.clearSharePrefValues()}")
//
//                navController.navigate(Graph.AUTHENTICATION) {
//                    popUpTo(Graph.ROOT) { inclusive = true }
//                }
//
//            }
//        }
//
//
////        is LogoutViewState.LogoutSuccess -> {
////
////            MainViewModel.forceLogoutUser(flowDetails = LogoutFlow.NORMAL_LOGOUT)
////            if (!showToast) {
////                // Show toast message only once
////                showToast = true
////                Log.d("Logout", "Logout successful")
////                Toast.makeText(context, "Log out successful", Toast.LENGTH_SHORT).show()
////
////                // Clear shared preferences and potentially app cache
////                viewModel.clearSharePrefValues()
////
////                navController.navigate(Graph.AUTHENTICATION) {
////                    popUpTo(AuthScreen.Login.route) { inclusive = true }
////                }
////            }
//
//
////            MainViewModel.forceLogoutUser(flowDetails = LogoutFlow.NORMAL_LOGOUT)
////            Toast.makeText(context, "Log out successful", Toast.LENGTH_SHORT).show()
////            settingsViewModel.clearSharedPreferences()
////            navController.navigate(Graph.AUTHENTICATION) {
////                popUpTo(Graph.ROOT) { inclusive = true }
////            }
//////            navController.navigate(Graph.AUTHENTICATION) {
//////                popUpTo(AuthScreen.Login.route) { inclusive = true }
//////            }
//        //       }
//        is LogoutViewState.DeviceDeletionComplete -> {
//            viewModel.logout()
//        }
//
//        else -> Unit
//    }
//
//
////    when (logoutState) {
////        is LogoutViewState.Error -> {
////            errorMessage = (logoutState as LoginState.Error).message
////            isLoading = false
////            Log.d("Login ERROR", "$errorMessage")
////            Toast.makeText(LocalContext.current, errorMessage ?: "", Toast.LENGTH_SHORT).show()
////        }
////
//////        is LogoutViewState.LogoutSuccess -> {
//////            MainViewModel.forceLogoutUser(flowDetails = LogoutFlow.NORMAL_LOGOUT)
//////        }
////        is LogoutViewState.LogoutSuccess -> {
////            MainViewModel.forceLogoutUser(flowDetails = LogoutFlow.NORMAL_LOGOUT)
////            if (!showToast) {
////                // Show toast message only once
////                showToast = true
////                Log.d("Logout", "Logout successful")
////                Toast.makeText(context, "Log out successful", Toast.LENGTH_SHORT).show()
////
////                // Clear shared preferences and potentially app cache
////                viewModel.clearSharePrefValues()
////
////                navController.navigate(Graph.AUTHENTICATION) {
////                    popUpTo(AuthScreen.Login.route) { inclusive = true }
////                }
////            }
////        }
////
////
////        is LogoutViewState.DeviceDeletionComplete -> {
////            viewModel.logout()
////        }
////
////        else -> {
////            // Idle state
////        }
////    }
//}
//
//@Composable
//fun ListItemSettings(text: String, icon: Int, onListClick: () -> Unit) {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(color = Color.White)
//            .wrapContentHeight()
//            .clickable { // Add click listener
////                if (text == getString(R.string.logout_settings)) {
////                    logoutViewModel.removeDevice() // Initiate device deletion
////                } else {
////                    // Handle other settings items here
////                }
//                onListClick.invoke()
//            }
//    ) {
//
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//                .wrapContentHeight(),
//        ) {
//            Icon(
//                painter = painterResource(id = icon),
//                contentDescription = "settings"
//            )
//            Spacer(modifier = Modifier.width(24.dp))
//
//            Text(text = text, fontSize = 12.sp)
//        }
//        Divider(
//            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
//        )
//
//    }
//
//}
//
//
//@Preview
//@Composable
//private fun PreviewSettings() {
//    SettingsScreen(navController = rememberNavController())
//
//}