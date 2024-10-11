package com.example.dovedrop.presentation.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dovedrop.presentation.navigation.AppScreens
import com.example.dovedrop.presentation.ui.components.EmailInputField
import com.example.dovedrop.presentation.ui.components.LongButton
import com.example.dovedrop.presentation.ui.components.NameInputField
import com.example.dovedrop.presentation.ui.components.PassWordInputField
import com.example.dovedrop.presentation.viewmodel.AuthViewModel

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    navController: NavController,
    isNetworkConnected: Boolean
) {
    val uiState by authViewModel.signUpUiState.collectAsState()
    var isSignUpButtonEnabled by remember { mutableStateOf(false) }

    BackHandler {
        navController.navigate(AppScreens.Login.route) {
            popUpTo(AppScreens.Login.route) { inclusive = false }
        }
        authViewModel.clearSignUpCred()
    }

    LaunchedEffect(authViewModel.supabase) {
        if(authViewModel.supabase == null) {
            navController.navigate(AppScreens.NoInternet.route) {
                popUpTo(AppScreens.NoInternet.route) { inclusive = false }
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
            .padding(vertical = 20.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        Text(
            text = "Dove Drop",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(Modifier.height(56.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(36.dp),
            modifier = Modifier
                .padding(horizontal = 12.dp)
        ){
            NameInputField(
                name = uiState.name,
                onNameChange = { authViewModel.changeEnteredSignUpName(it) },
                modifier = Modifier
            )
            EmailInputField(
                emailAddress = uiState.email,
                onEmailChange = {
                    authViewModel.changeEnteredSignUpEmail(it)
                    isSignUpButtonEnabled = authViewModel.signUpUiState.value.password.isNotEmpty() &&
                            authViewModel.signUpUiState.value.email.isNotEmpty()
                                },
                modifier = Modifier
            )
            PassWordInputField(
                password = uiState.password,
                isPasswordVisible = uiState.isPasswordVisible,
                onPasswordChange = {
                    authViewModel.changeEnteredSignUpPassword(it)
                    isSignUpButtonEnabled = authViewModel.signUpUiState.value.password.isNotEmpty() &&
                            authViewModel.signUpUiState.value.email.isNotEmpty()
                                   },
                onPasswordVisibilityChange = { authViewModel.changePasswordVisibility(it) }
            )
            Spacer(Modifier.height(20.dp))
            LongButton(
                text = "SIGN UP",
                onClick = { authViewModel.signUpUser() },
                isEnabled = isSignUpButtonEnabled
            )
        }
    }
}
