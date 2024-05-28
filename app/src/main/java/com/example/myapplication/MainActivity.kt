package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.graphs.AuthScreen
import com.example.myapplication.graphs.Graph
import com.example.myapplication.graphs.RootNavigationGraph
import com.example.myapplication.home.HomeScreen
import com.example.myapplication.login.LoginScreen
import com.example.myapplication.pref.SharedPref
import com.example.myapplication.profile.ProfileScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.utils.LogoutFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()


        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                RootNavigationGraph(navController = navController)
            }
        }





    }

//    private fun observeForceLogout(navController: NavController,onClearSharedPref: () -> Unit) {
//        MainViewModel.forceLogout.observe(this@MainActivity) { flow ->
//            when (flow) {
//                LogoutFlow.REFRESH_TOKEN_EXPIRED -> {
//                    //no op
//                }
//                /*LogoutFlow.INACTIVE_USER -> {
//                    //no op
//                }*/
//            }
//            forceLogOutUser(navController = navController,onClearSharedPref=onClearSharedPref)
//        }
//    }

//    private fun forceLogOutUser(onClearSharedPref: () -> Unit,navController: NavController) {
//       onClearSharedPref.invoke()
//        navController.navigate(Graph.AUTHENTICATION) {
//            popUpTo(Graph.ROOT) { inclusive = true }
//        }
//    }

}



