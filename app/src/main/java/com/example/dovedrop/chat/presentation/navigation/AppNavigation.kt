package com.example.dovedrop.chat.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.dovedrop.chat.presentation.ui.screens.auth.AuthViewModel
import com.example.dovedrop.chat.presentation.ui.screens.auth.LoginScreen
import com.example.dovedrop.chat.presentation.ui.screens.auth.reset_password.ResetPasswordScreen
import com.example.dovedrop.chat.presentation.ui.screens.auth.SignUpScreen
import com.example.dovedrop.chat.presentation.ui.screens.auth.t_and_c.TermsAndConditionsScreen
import com.example.dovedrop.chat.presentation.ui.screens.main.chat.chat_list.ChatListHomeScreen
import com.example.dovedrop.chat.presentation.ui.screens.onboard.OnBoardingScreen
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation(
) {
    val appNavController = rememberNavController()
    val authViewModel: AuthViewModel = koinViewModel()
    val backStack by appNavController.currentBackStackEntryAsState()
    val currentDestination = backStack?.destination

    Scaffold {
        NavHost(
            navController = appNavController,
            startDestination = AppNavGraph.AuthNavGraph
        ) {
            // Auth nav subgraph
            authNavigation(
                appNavController = appNavController
            )

            // Main screens nav subgraph
            mainNavigation(
                appNavController = appNavController,
                logout = { authViewModel.logOutUser() },
                currentDestination = currentDestination
            )
        }
    }

}
