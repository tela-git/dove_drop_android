package com.example.dovedrop.chat.presentation.ui.screens.main.chat.chat_list

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import com.example.dovedrop.R
import com.example.dovedrop.chat.presentation.navigation.AppNavGraph
import com.example.dovedrop.chat.presentation.ui.components.app_bars.AppBottomBar
import com.example.dovedrop.chat.presentation.ui.components.app_bars.ChatsHomeTopBar
import com.example.dovedrop.chat.presentation.ui.components.loading.LoadingPage
import com.example.dovedrop.chat.presentation.ui.screens.main.chat.ChatViewModel
import kotlinx.coroutines.flow.collect
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatListHomeScreen(
    currentDestination: NavDestination?,
    onBottomBarIconClick: (AppNavGraph) -> Unit,
    onMoreOptionsClick: () -> Unit,
    onAddChatRoomClick: () -> Unit,
) {
    val chatViewModel : ChatViewModel = koinViewModel()
    val chatHomeUIState by chatViewModel.chatHomeUIState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        //chatViewModel.getAllChatRooms()
    }
    LaunchedEffect(Unit) {
        chatViewModel.chatHomeToastFlow.collect { message->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
    Scaffold(
        topBar = {
            ChatsHomeTopBar(
                onMoreOptionsClick = {
                    chatViewModel.updateMoreOptionsVisibility()
                },
                isNavigateBackIconVisible = false,
                onRefresh = {
                    chatViewModel.getAllChatRooms()
                }
            )
        },
        bottomBar = {
            AppBottomBar(
                currentDestination = currentDestination,
                onBottomNavItemClick = onBottomBarIconClick
            )
        },
        floatingActionButton = {
            AddChatRoomButton {
                onAddChatRoomClick()
            }
        },
        modifier = Modifier
    ) { innerPadding->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
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
            }
            items(
                items = chatHomeUIState.chatRooms ?: emptyList(),
                key = { it.id }
            ) {chatRoomDetails->
                ChatRoomCard(
                    chatRoom = chatRoomDetails,
                    onClick = { }
                )
            }
        }
    }
    if(chatHomeUIState.isLoading) {
        LoadingPage()
    }
}

@Composable
private fun AddChatRoomButton(
    onButtonClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onButtonClick
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.add_new_chat),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
}