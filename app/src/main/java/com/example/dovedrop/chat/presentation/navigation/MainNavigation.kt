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
fun MainNavigation(
) {
    val mainNavController = rememberNavController()
    val authViewModel: AuthViewModel = koinViewModel()

    NavHost(
        navController = mainNavController,
        startDestination = AppNavGraph.AuthNavGraph
    ) {
        navigation<AppNavGraph.AuthNavGraph>(
            startDestination = AppNavGraph.AuthNavGraph.OnBoarding,
        ) {
            composable<AppNavGraph.AuthNavGraph.OnBoarding> {
                OnBoardingScreen(
                    onContinueClick = {
                        mainNavController.navigate(AppNavGraph.AuthNavGraph.Login)
                    },
                    navigateToHome = {
                        mainNavController.navigate(AppNavGraph.MainNavGraph.Home) {
                            popUpTo(AppNavGraph.AuthNavGraph) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable<AppNavGraph.AuthNavGraph.Login> {
                LoginScreen(
                    onGoToSignUpClick = {
                        mainNavController.navigate(AppNavGraph.AuthNavGraph.Signup)
                    },
                    onForgotPasswordClick = {
                        mainNavController.navigate(AppNavGraph.AuthNavGraph.ResetPassword)
                    },
                    onLoginSuccess = {
                        mainNavController.navigate(AppNavGraph.MainNavGraph) {
                            popUpTo(AppNavGraph.AuthNavGraph) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable<AppNavGraph.AuthNavGraph.Signup> {
                SignUpScreen(
                    onGoToLoginClick = {
                        mainNavController.navigate(AppNavGraph.AuthNavGraph.Login) {
                            popUpTo(AppNavGraph.AuthNavGraph.Login) {
                                inclusive = true
                            }
                        }
                    },
                    onTAndCClick = {
                        mainNavController.navigate(AppNavGraph.AuthNavGraph.TermsAndConditions)
                    }
                )
            }
            composable<AppNavGraph.AuthNavGraph.ResetPassword> {
                ResetPasswordScreen(
                    modifier = Modifier
                )
            }
            composable<AppNavGraph.AuthNavGraph.TermsAndConditions> {
                TermsAndConditionsScreen()
            }
        }
        navigation<AppNavGraph.MainNavGraph>(
            startDestination = AppNavGraph.MainNavGraph.Home
        ) {
            composable<AppNavGraph.MainNavGraph.Home> {
                ChatListHomeScreen(
                    onLogoutClick = {
                        authViewModel.logOutUser()
                        mainNavController.navigate(AppNavGraph.AuthNavGraph) {
                            popUpTo(AppNavGraph.MainNavGraph)
                        }
                    }
                )
            }
        }
    }

}
