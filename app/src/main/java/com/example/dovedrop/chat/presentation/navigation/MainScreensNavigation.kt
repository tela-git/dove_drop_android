package com.example.dovedrop.chat.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.dovedrop.chat.presentation.ui.screens.chat.chat_list.ChatListHomeScreen

fun NavGraphBuilder.mainNavigation(
    appNavController: NavHostController,
    logout: () -> Unit,
) {
    navigation<AppNavGraph.MainNavGraph>(
        startDestination = AppNavGraph.MainNavGraph.Home
    ) {
        composable<AppNavGraph.MainNavGraph.Home> {
            ChatListHomeScreen(
                onLogoutClick = {
                    logout()
                    appNavController.navigate(AppNavGraph.AuthNavGraph) {
                        popUpTo(AppNavGraph.MainNavGraph)
                    }
                }
            )
        }
    }
}