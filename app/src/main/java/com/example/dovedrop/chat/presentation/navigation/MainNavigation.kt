package com.example.dovedrop.chat.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.dovedrop.chat.presentation.ui.screens.chat.calls.CallsHomeScreen
import com.example.dovedrop.chat.presentation.ui.screens.chat.chat_list.ChatListHomeScreen

fun NavGraphBuilder.mainNavigation(
    appNavController: NavHostController,
    logout: () -> Unit,
    currentDestination: NavDestination?
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
                },
                onBottomBarIconClick = { route->
                    appNavController.navigate(route) {
                        popUpTo<AppNavGraph.MainNavGraph.Home> {
                            inclusive = false
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                currentDestination = currentDestination
            )
        }
        composable<AppNavGraph.MainNavGraph.Calls> {
            CallsHomeScreen(
                currentDestination = currentDestination,
                onBottomBarIconClick = {route->
                    appNavController.navigate(route) {
                        popUpTo<AppNavGraph.MainNavGraph.Home> {
                            inclusive = false
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}