package com.example.dovedrop.presentation.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.waterfall
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.dovedrop.presentation.navigation.AppScreens
import com.example.dovedrop.presentation.ui.components.AppBottomBar
import com.example.dovedrop.presentation.ui.components.AppFloatingActionButton
import com.example.dovedrop.presentation.ui.components.AppTopBar
import com.example.dovedrop.presentation.ui.components.ChatFloatingActionButton
import com.example.dovedrop.presentation.viewmodel.AppAuthState
import com.example.dovedrop.presentation.viewmodel.AuthViewModel
import com.example.dovedrop.presentation.viewmodel.ChatViewModel
import com.example.dovedrop.presentation.viewmodel.MessageUiState
import com.example.dovedrop.presentation.viewmodel.UiMessage
import io.github.jan.supabase.auth.auth

@Composable
fun ChatListScreen(
    authViewModel: AuthViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
    isNetworkConnected: Boolean
) {
    val authState by authViewModel.authState.collectAsState()
    val auth = authViewModel.supabase?.auth
    val chatViewModel: ChatViewModel = hiltViewModel()
    val chatListUiState = chatViewModel.chatListUiState.collectAsState()
    var isNavigateBackVisible by remember { mutableStateOf(false) }

    BackHandler {
        // Do nothing
    }
    LaunchedEffect(authState) {
        if(auth != null) {
            if (
                authState.authState == AppAuthState.UnAuthenticated
            ) {
                navController.navigate(AppScreens.OnBoarding.route) {
                    popUpTo(AppScreens.OnBoarding.route)
                }
            }
        }
    }
    LaunchedEffect(isNetworkConnected) {
        if(!isNetworkConnected) {
            navController.navigate(AppScreens.NoInternet.route) {
                popUpTo(AppScreens.NoInternet.route) { inclusive = true }
            }
        }
    }
    Scaffold(
        topBar = {
            AppTopBar(
                onSearchIconClick = {
                    chatViewModel.getChatList()
                },
                onAccountIconClick = { },
                onSettingsIconClick = { },
                isNavigateBackIconVisible = isNavigateBackVisible
            )
        },
        bottomBar = {
            AppBottomBar(

            )
        },
        modifier = Modifier,
        floatingActionButton = {
            ChatFloatingActionButton(
                onChatIconClick = { }
            )

        },
    ){ innerPadding->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
//                .border(width = 2.dp, color = Color.Red),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ChatMessagesBody(
                uiState = chatListUiState.value
            )
        }
    }
}

@Composable
fun ChatMessagesBody(
    uiState: MessageUiState
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items  = uiState.messagesList
        ) { item->
            ChatMessage(
                message = item
            )
        }
    }
}

@Composable
fun ChatMessage(message: UiMessage) {
    ElevatedCard(
        modifier = Modifier
            .sizeIn(
                minHeight = 50.dp,
                minWidth = 50.dp
            ),
        shape = RoundedCornerShape(20.dp, 20.dp, 0.dp, 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFEFD9)
        )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(0.8f)
        ) {
            Text(
                text = message.message,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text =message.creationTime,
                textAlign = TextAlign.End,
                fontSize = 8.sp
            )
        }
    }
}