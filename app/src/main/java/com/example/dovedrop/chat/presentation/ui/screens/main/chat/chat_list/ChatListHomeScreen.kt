package com.example.dovedrop.chat.presentation.ui.screens.main.chat.chat_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import com.example.dovedrop.chat.presentation.navigation.AppNavGraph
import com.example.dovedrop.chat.presentation.ui.components.app_bars.AppBottomBar
import com.example.dovedrop.chat.presentation.ui.components.app_bars.ChatsHomeTopBar
import com.example.dovedrop.chat.presentation.ui.screens.main.chat.ChatViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatListHomeScreen(
    currentDestination: NavDestination?,
    onBottomBarIconClick: (AppNavGraph) -> Unit,
    onMoreOptionsClick: () -> Unit,
) {
    val chatViewModel : ChatViewModel = koinViewModel()
    val chatHomeUIState by chatViewModel.chatHomeUIState.collectAsState()

    Scaffold(
        topBar = {
            ChatsHomeTopBar(
                onMoreOptionsClick = {
                    chatViewModel.updateMoreOptionsVisibility()
                },
                isNavigateBackIconVisible = false
            )
        },
        bottomBar = {
            AppBottomBar(
                currentDestination = currentDestination,
                onBottomNavItemClick = onBottomBarIconClick
            )
        }
    ) { innerPadding->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DropdownMenu(
                expanded = chatHomeUIState.isMoreOptionsVisibility,
                onDismissRequest = chatViewModel::updateMoreOptionsVisibility,
                offset = DpOffset(x = 800.dp, y = 64.dp)
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = "Settings"
                        )
                    },
                    onClick = {
                        onMoreOptionsClick()
                        chatViewModel.updateMoreOptionsVisibility()
                    }
                )
            }
            Text(
                text = "You are logged in. \n This is home screen"
            )
        }

    }
}