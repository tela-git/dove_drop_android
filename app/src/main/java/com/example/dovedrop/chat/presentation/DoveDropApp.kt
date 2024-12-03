package com.example.dovedrop.chat.presentation

import ChatListScreen
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dovedrop.chat.data.network.NetworkConnectionState
import com.example.dovedrop.chat.data.network.rememberConnectivityState
import com.example.dovedrop.chat.domain.util.toString
import com.example.dovedrop.chat.presentation.navigation.AppScreens
import com.example.dovedrop.chat.presentation.ui.screens.AppViewModel
import com.example.dovedrop.chat.presentation.ui.screens.NoInternetScreen
import com.example.dovedrop.chat.presentation.ui.screens.auth.AuthEvents
import com.example.dovedrop.chat.presentation.ui.screens.auth.AuthViewModel
import com.example.dovedrop.chat.presentation.ui.screens.auth.LoginScreen
import com.example.dovedrop.chat.presentation.ui.screens.auth.SignUpScreen
import com.example.dovedrop.chat.presentation.ui.screens.chat.ChatViewModel
import com.example.dovedrop.chat.presentation.ui.screens.chat.chat_detail.ChatDetailScreen
import com.example.dovedrop.chat.presentation.ui.screens.contacts.ContactEvents
import com.example.dovedrop.chat.presentation.ui.screens.contacts.ContactViewModel
import com.example.dovedrop.chat.presentation.ui.screens.onboard.OnBoardingScreen
import com.example.dovedrop.chat.presentation.utils.ObserveAsEvents

@Composable
fun DoveDropApp(
) {
    val appViewModel: AppViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()
    val contactViewModel: ContactViewModel = hiltViewModel()
    val chatViewModel: ChatViewModel = hiltViewModel()

    val navController = rememberNavController()
    val context = LocalContext.current
    val connectionState by rememberConnectivityState()
    val isNetworkConnected by remember(connectionState) {
        derivedStateOf {
            connectionState === NetworkConnectionState.Available
        }
    }
    val isUserLoggedIn by authViewModel.authState.collectAsState()
    var isLogoutDialogVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {

        },
        bottomBar = {

        }
    ) { innerPadding->

        ObserveAsEvents(authViewModel.events) {event->
            when(event) {
                is AuthEvents.Error -> {
                    Toast.makeText(
                        context,
                        event.error.toString(context),
                        Toast.LENGTH_LONG
                    ).show()
                }
                is AuthEvents.Success -> {
                    Toast.makeText(
                        context,
                        event.success,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        ObserveAsEvents(contactViewModel.events) {event->
            when(event) {
                is ContactEvents.ContactAddedSuccessfully -> {
                    Toast.makeText(
                        context,
                        "contact added successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                ContactEvents.ContactAlreadyExists ->  {
                    Toast.makeText(
                        context,
                        "contact already exists",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                ContactEvents.FailedToAddContact ->  {
                    Toast.makeText(
                        context,
                        "Failed to add contact",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        NavHost(
            navController = navController,
            startDestination = AppScreens.OnBoarding.route
        ) {
            composable(
                route = AppScreens.OnBoarding.route
            ) {
                LaunchedEffect(Unit) {
                    if(isUserLoggedIn) {
                        navController.navigate(AppScreens.ChatList.route) {
                            popUpTo(AppScreens.ChatList.route)
                        }
                    }
                }
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
                        .padding(innerPadding),
                    isNetworkConnected = isNetworkConnected
                )
            }
            composable(
                route = AppScreens.ChatList.route
            ) {
                LaunchedEffect(key1 = isUserLoggedIn) {
                    if(!isUserLoggedIn) {
                        navController.navigate(AppScreens.Login.route) {
                            popUpTo(AppScreens.Login.route)
                        }
                    }
                }
                ChatListScreen(
                    modifier = Modifier
                        .padding(innerPadding),
                    isLogoutDialogVisible = isLogoutDialogVisible,
                    changeLogoutDialogVisibility = { isLogoutDialogVisible = it },
                    auth = authViewModel.auth,
                    onLogoutClick = {
                        authViewModel.logoutUser()
                        isLogoutDialogVisible = false
                    },
                    onSearchIconClick = {  },
                    chatRoomsList =  chatViewModel.chatRoomsList.collectAsState(),
                    contactViewModel = contactViewModel,
                    onChatRoomCardClick = {chatRoomDetailed->
                        chatViewModel.updateCurrentChatRoom(chatRoomDetailed)
                        navController.navigate(AppScreens.ChatDetail.route) {
                            popUpTo(AppScreens.ChatList.route)
                        }
                    },
                )
            }
            composable(
                route = AppScreens.SignUp.route
            ) {
                SignUpScreen(
                    modifier = Modifier
                        .padding(innerPadding),
                    authViewModel = authViewModel,
                    navController = navController,
                    isNetworkConnected = isNetworkConnected
                )
            }
            composable(
                route = AppScreens.NoInternet.route
            ) {
                NoInternetScreen(
                    modifier = Modifier
                        .padding(innerPadding),
                    authViewModel = authViewModel,
                    navController = navController,
                    isNetworkConnected = isNetworkConnected
                )
            }
            composable(
                route = AppScreens.ChatDetail.route
            ) {
                ChatDetailScreen(
                    contactEmail = "1234@gmail.com",
                    navController = navController,
                    chatViewModel = chatViewModel
                )
            }
        }
    }
}
