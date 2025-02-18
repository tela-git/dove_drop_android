package com.example.dovedrop.chat.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.dovedrop.chat.presentation.ui.screens.auth.AuthViewModel
import com.example.dovedrop.chat.presentation.ui.screens.auth.LoginScreen
import com.example.dovedrop.chat.presentation.ui.screens.auth.reset_password.ResetPasswordScreen
import com.example.dovedrop.chat.presentation.ui.screens.auth.SignUpScreen
import com.example.dovedrop.chat.presentation.ui.screens.auth.t_and_c.TermsAndConditionsScreen
import com.example.dovedrop.chat.presentation.ui.screens.chat.chat_list.ChatListHomeScreen
import com.example.dovedrop.chat.presentation.ui.screens.onboard.OnBoardingScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(
) {
    val appNavController = rememberNavController()
    val authViewModel: AuthViewModel = koinViewModel()

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
            logout = { authViewModel.logOutUser() }
        )
    }

}
