package com.example.dovedrop.presentation.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dovedrop.data.network.NetworkConnectionState
import com.example.dovedrop.data.network.rememberConnectivityState
import com.example.dovedrop.presentation.navigation.AppScreens
import com.example.dovedrop.presentation.ui.components.EmailInputField
import com.example.dovedrop.presentation.ui.components.LongButton
import com.example.dovedrop.presentation.ui.components.PassWordInputField
import com.example.dovedrop.presentation.viewmodel.AppAuthState
import com.example.dovedrop.presentation.viewmodel.AuthViewModel
import com.example.dovedrop.presentation.viewmodel.LoginUiState
import io.github.jan.supabase.auth.auth

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    navController: NavController,
    isNetworkConnected: Boolean
) {
    val authState by authViewModel.authState.collectAsState()
    val uiState by authViewModel.loginUiState.collectAsState()
    var isLoginEnabled by remember { mutableStateOf(false) }

    BackHandler {
        // Do nothing
    }

    LaunchedEffect(authState) {
        if(
            authState.authState == AppAuthState.Authenticated
        ) {
            navController.navigate(AppScreens.ChatList.route) {
                popUpTo(AppScreens.ChatList.route) { inclusive = false }
            }
        }
    }
    LaunchedEffect(isNetworkConnected) {
        if(!isNetworkConnected){
            navController.navigate(AppScreens.NoInternet.route) {
                popUpTo(AppScreens.NoInternet.route) { inclusive = true }
            }
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Dove Drop",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 20.dp)
        )
        LoginBody(
            uiState = uiState,
            onPasswordChange = {
                authViewModel.changeEnteredLoginPassword(it)
                isLoginEnabled = authViewModel.loginUiState.value.password.isNotEmpty() &&
                        authViewModel.loginUiState.value.email.isNotEmpty()
                               },
            onEmailChange = {
                authViewModel.changeEnteredLoginEmail(it)
            isLoginEnabled = authViewModel.loginUiState.value.email.isNotEmpty() &&
                    authViewModel.loginUiState.value.password.isNotEmpty()
                            },
            onPasswordVisibilityChange = { authViewModel.changePasswordVisibility(it) },
            isLoginEnabled = isLoginEnabled,
            onLoginButtonClick = { authViewModel.loginUser() },
            onSignUpInsteadClick = {
                navController.navigate(AppScreens.SignUp.route) {
                    popUpTo(AppScreens.SignUp.route) { inclusive = false }
                }
                authViewModel.clearLoginCred()
            }
        )
    }
}

@Composable
fun LoginBody(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibilityChange: (Boolean) -> Unit,
    isLoginEnabled: Boolean,
    onSignUpInsteadClick: () -> Unit,
    onLoginButtonClick: () ->  Unit
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        Column(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmailInputField(
                emailAddress = uiState.email,
                onEmailChange = onEmailChange
            )
        }
        Column(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PassWordInputField(
                password = uiState.password,
                onPasswordChange =  onPasswordChange,
                isPasswordVisible = uiState.isPasswordVisible,
                onPasswordVisibilityChange = onPasswordVisibilityChange
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = modifier
                    .fillMaxWidth()
            ){
                TextButton(
                    onClick = { }
                ) {
                    Text(
                        text = "Forgot password?",
                        style = MaterialTheme.typography.labelLarge,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ){
        LongButton(
            text = "LOGIN",
            onClick = {
                onLoginButtonClick()
            },
            modifier = Modifier
                .padding(horizontal = 16.dp),
            isEnabled = isLoginEnabled
        )
        TextButton(
            onClick = {
                onSignUpInsteadClick()
            }
        ) {
            Row()
            {
                Text(
                    text = "Don't have an account ?",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textDecoration = TextDecoration.Underline
                    )
                Text(
                    text = " Sign Up ",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
    Spacer(Modifier.height(10.dp))
}
