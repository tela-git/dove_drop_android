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
import com.example.dovedrop.chat.presentation.ui.screens.main.chat.calls.CallsHomeScreen
import com.example.dovedrop.chat.presentation.ui.screens.main.chat.chat_list.ChatListHomeScreen
import com.example.dovedrop.chat.presentation.ui.screens.main.contacts.AddChatRoomScreen
import com.example.dovedrop.chat.presentation.ui.screens.main.settings.SettingsHomeScreen
import kotlin.math.log

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
                onBottomBarIconClick = { route->
                    appNavController.navigate(route) {
                        popUpTo<AppNavGraph.MainNavGraph.Home> {
                            inclusive = false
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                currentDestination = currentDestination,
                onMoreOptionsClick = {
                    appNavController.navigate(AppNavGraph.MainNavGraph.SettingsHome)
                },
                onAddChatRoomClick = {
                    appNavController.navigate(AppNavGraph.MainNavGraph.AddChatRoom)
                }
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
        composable<AppNavGraph.MainNavGraph.SettingsHome> {
            SettingsHomeScreen(
                onNavigateUp = {
                    appNavController.navigateUp()
                },
                onLogoutClick = {
                    logout()
                    appNavController.navigate(AppNavGraph.AuthNavGraph) {
                        popUpTo<AppNavGraph.MainNavGraph> {
                            inclusive = true
                        }
                    }
                },
            )
        }
        composable<AppNavGraph.MainNavGraph.AddChatRoom> {
            AddChatRoomScreen(
                onNavigateUp = {
                    appNavController.navigateUp()
                }
            )
        }
    }
}