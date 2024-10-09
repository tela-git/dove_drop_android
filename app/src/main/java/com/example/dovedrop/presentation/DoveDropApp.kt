package com.example.dovedrop.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dovedrop.presentation.navigation.AppScreens
import com.example.dovedrop.presentation.ui.screens.OnBoardingScreen

@Composable
fun DoveDropApp(
) {
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
                        .padding(innerPadding)
                        .fillMaxSize(),
                    navController = navController
                )
            }
        }
    }

}
