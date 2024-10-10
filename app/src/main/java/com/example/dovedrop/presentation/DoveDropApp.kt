package com.example.dovedrop.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.saveable.autoSaver
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dovedrop.presentation.navigation.AppScreens
import com.example.dovedrop.presentation.ui.screens.ChatListScreen
import com.example.dovedrop.presentation.ui.screens.LoginScreen
import com.example.dovedrop.presentation.ui.screens.OnBoardingScreen
import com.example.dovedrop.presentation.viewmodel.AuthViewModel

@Composable
fun DoveDropApp(
) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val navController = rememberNavController()

    Scaffold(
        topBar = { },
        bottomBar = { }
    ) {innerPadding->
        NavHost(
            navController = navController,
            startDestination = AppScreens.OnBoarding.route
        ) {
            composable(
                route = AppScreens.OnBoarding.route
            ) {
                OnBoardingScreen(
                    modifier = Modifier
                        .padding(innerPadding),
                    navController = navController,
                    authViewModel = authViewModel
                )
            }
            composable(
                route = AppScreens.Login.route
            ) {
                LoginScreen(
                    navController = navController,
                    authViewModel = authViewModel,
                    modifier = Modifier
                        .padding(innerPadding)
                )
            }
            composable(
                route = AppScreens.ChatList.route
            ) {
                ChatListScreen(
                    modifier = Modifier
                        .padding(innerPadding),
                    authViewModel = authViewModel,
                    navController = navController
                )
            }
        }
    }

}
