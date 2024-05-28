package com.example.myapplication.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.components.CustomGradientButton
import com.example.myapplication.components.CustomInputTextField
import com.example.myapplication.components.CustomTextView
import com.example.myapplication.components.PasswordVisibilityOutlinedTextField
import com.example.myapplication.graphs.AuthScreen
import com.example.myapplication.graphs.Graph

@Composable
fun LoginScreen(
    navController: NavHostController
) {

    var showToast by remember { mutableStateOf(false) }


//    var emailId by remember { mutableStateOf(TextFieldValue()) }
//    var password by remember { mutableStateOf(TextFieldValue()) }
    var errorStatus by remember { mutableStateOf(false) }


    var isPasswordVisible by remember { mutableStateOf(false) }

    val viewModel = hiltViewModel<LoginViewModel>()

    val username by viewModel.username
    val password by viewModel.password
    val usernameError by viewModel.usernameError
    val passwordError by viewModel.passwordError
    val loginState by viewModel.loginState.collectAsState()


    Column(
        modifier = Modifier
            .background(Color(0xFFF2F5F7))
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(modifier = Modifier.height(44.dp))

        Image(
            painter = painterResource(R.drawable.ic_logo_with_title),
            contentDescription = "Logo"
        )

        Spacer(modifier = Modifier.height(48.dp))

        Text(
            text = stringResource(id = R.string.login_or_register),
            color = Color(0xFF424242),
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.font_medium))
        )
        Spacer(modifier = Modifier.height(26.dp))


        CustomTextView(
            text = stringResource(id = R.string.mail_id),
            mandatory = true
        )
        Spacer(modifier = Modifier.height(10.dp))

        CustomInputTextField(
            value = username,
            onValueChange = { viewModel.username.value = it },
            placeholder = stringResource(id = R.string.enter_mail_id),
            isError = viewModel.isUsernameError,
            errorMessageResId = viewModel.usernameError.value
        )
        Spacer(modifier = Modifier.height(35.dp))

        CustomTextView(
            text = stringResource(id = R.string.password),
            mandatory = true
        )
        Spacer(modifier = Modifier.height(10.dp))

        PasswordVisibilityOutlinedTextField(
            value = password,
            onValueChange = { viewModel.password.value = it },
            placeholder = stringResource(id = R.string.enter_password),
            isPasswordVisible = isPasswordVisible,
            onTogglePasswordVisibility = { isPasswordVisible = !isPasswordVisible },
            isError = viewModel.isPasswordError,
            errorMessageResId = viewModel.passwordError.value
        )

//        CustomInputTextField(
//            value = password,
//            onValueChange = {password = it},
//            placeholder = stringResource(id = R.string.enter_password)
//        )

        Spacer(modifier = Modifier.height(10.dp))

        HelpText()

        Spacer(modifier = Modifier.height(48.dp))

        CustomGradientButton(
            text = stringResource(id = R.string.login),
            onClick = { viewModel.validateInput() }
        )

    }

    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }


    // Observing Login State
    when (loginState) {
        is LoginState.Error -> {
            errorMessage = (loginState as LoginState.Error).message
            isLoading = false
            Log.d("Login ERROR", "$errorMessage")
            Toast.makeText(LocalContext.current, errorMessage ?: "", Toast.LENGTH_SHORT).show()
        }

        is LoginState.Loading -> {
            isLoading = (loginState as LoginState.Loading).loading
        }

        is LoginState.Success -> {
            if (!showToast) {
                // Show toast message only once
                showToast = true
                Log.d("Login", "Login successful")
                Toast.makeText(
                    LocalContext.current,
                    "Login successful",
                    Toast.LENGTH_SHORT
                ).show()

                // Navigate to the home screen
                // navController.navigate("home")
                navController.navigate(Graph.MAIN_SCREEN_PAGE) {
                    popUpTo(AuthScreen.Login.route) { inclusive = true }
                }
            }
        }

        else -> {
            // Idle state
        }
    }

}


@Composable
private fun HelpText() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End,
    ) {
        Text(
            text = stringResource(id = R.string.help),
            // textAlign = TextAlign.End,
            fontFamily = FontFamily(Font(R.font.font_medium)),
            fontSize = 12.sp,
            color = colorResource(id = R.color.help_text_color),
            modifier = Modifier
                .clickable { }
        )
    }
}
