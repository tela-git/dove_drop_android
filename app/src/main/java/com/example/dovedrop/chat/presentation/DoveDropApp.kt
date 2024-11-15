package com.example.dovedrop.chat.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dovedrop.chat.data.network.NetworkConnectionState
import com.example.dovedrop.chat.data.network.rememberConnectivityState
import com.example.dovedrop.chat.presentation.navigation.AppScreens
import com.example.dovedrop.chat.presentation.ui.screens.ChatDetailScreen
import com.example.dovedrop.chat.presentation.ui.screens.ChatListScreen
import com.example.dovedrop.chat.presentation.ui.screens.ContactListScreen
import com.example.dovedrop.chat.presentation.ui.screens.LoginScreen
import com.example.dovedrop.chat.presentation.ui.screens.NoInternetScreen
import com.example.dovedrop.chat.presentation.ui.screens.OnBoardingScreen
import com.example.dovedrop.chat.presentation.ui.screens.SignUpScreen
import com.example.dovedrop.chat.presentation.viewmodel.AppViewModel
import com.example.dovedrop.chat.presentation.viewmodel.AuthViewModel

@Composable
fun DoveDropApp(
) {
    val appViewModel: AppViewModel = hiltViewModel()
    val authViewModel: AuthViewModel = hiltViewModel()
    val navController = rememberNavController()

    val connectionState by rememberConnectivityState()
    val isNetworkConnected by remember(connectionState) {
        derivedStateOf {
            connectionState === NetworkConnectionState.Available
        }
    }
    Scaffold(
        topBar = { },
        bottomBar = { }
    ) { innerPadding->
        NavHost(
            navController = navController,
            startDestination = AppScreens.OnBoarding.route
        ) {
            composable(
                route = AppScreens.OnBoarding.route
            ) {
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
                ChatListScreen(
                    modifier = Modifier
                        .padding(innerPadding),
                    authViewModel = authViewModel,
                    navController = navController,
                    isNetworkConnected = isNetworkConnected,
                    fetchContacts = appViewModel::getContacts
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

                )
            }
            composable(
                route = AppScreens.ContactList.route
            ) {
                ContactListScreen(
                    modifier = Modifier
                        .padding(innerPadding),
                    navController = navController,
                    contactListUiState = appViewModel.contactListUiState.collectAsState().value
                )
            }

        }
    }


}
