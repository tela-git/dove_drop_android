package com.example.dovedrop.presentation.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.dovedrop.presentation.navigation.AppScreens
import com.example.dovedrop.presentation.viewmodel.AppAuthState
import com.example.dovedrop.presentation.viewmodel.AuthViewModel
import io.github.jan.supabase.auth.auth

@Composable
fun ChatListScreen(
    authViewModel: AuthViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val authState by authViewModel.authState.collectAsState()
    val auth = authViewModel.supabase.auth
    BackHandler {
        // Do nothing
    }
    LaunchedEffect(authState) {
        if(
            authState.authState == AppAuthState.UnAuthenticated
        ) {
            navController.navigate(AppScreens.OnBoarding.route) {
                popUpTo(AppScreens.OnBoarding.route)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "This is ChatListScreen"
        )
        Text(
            text = auth.currentUserOrNull()?.email ?: ""
        )
        Text(
            text = auth.currentUserOrNull()?.role ?: ""
        )
        Spacer(Modifier.height(20.dp))
        Button(
            onClick = {
                authViewModel.logoutUser()
            }
        ) {
            Text(
                text = "Log out"
            )
        }
    }
}