package com.example.dovedrop.chat.presentation.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.dovedrop.chat.presentation.ui.screens.auth.EmailVerificationScreen
import com.example.dovedrop.chat.presentation.ui.screens.auth.ForgotPasswordScreen
import com.example.dovedrop.chat.presentation.ui.screens.auth.LoginScreen
import com.example.dovedrop.chat.presentation.ui.screens.auth.SignUpScreen
import com.example.dovedrop.chat.presentation.ui.screens.auth.reset_password.ResetPasswordScreen
import com.example.dovedrop.chat.presentation.ui.screens.auth.t_and_c.TermsAndConditionsScreen
import com.example.dovedrop.chat.presentation.ui.screens.onboard.OnBoardingScreen

fun NavGraphBuilder.authNavigation(
    appNavController: NavHostController
) {
    navigation<AppNavGraph.AuthNavGraph>(
        startDestination = AppNavGraph.AuthNavGraph.OnBoarding,
    ) {
        composable<AppNavGraph.AuthNavGraph.OnBoarding> {
            OnBoardingScreen(
                onContinueClick = {
                    appNavController.navigate(AppNavGraph.AuthNavGraph.Login)
                },
                navigateToHome = {
                    appNavController.navigate(AppNavGraph.MainNavGraph.Home) {
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
                    appNavController.navigate(AppNavGraph.AuthNavGraph.Signup)
                },
                onForgotPasswordClick = {
                    appNavController.navigate(AppNavGraph.AuthNavGraph.ForgotPassword)
                },
                onLoginSuccess = {
                    appNavController.navigate(AppNavGraph.MainNavGraph) {
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
                    appNavController.navigate(AppNavGraph.AuthNavGraph.Login) {
                        popUpTo(AppNavGraph.AuthNavGraph.Login) {
                            inclusive = true
                        }
                    }
                },
                onTAndCClick = {
                    appNavController.navigate(AppNavGraph.AuthNavGraph.TermsAndConditions)
                },
                onSignUpComplete = {email->
                    appNavController.navigate(AppNavGraph.AuthNavGraph.EmailVerification(email))
                }
            )
        }
        composable<AppNavGraph.AuthNavGraph.EmailVerification> { navBackStack->
            val email = navBackStack.toRoute<AppNavGraph.AuthNavGraph.EmailVerification>().email
            EmailVerificationScreen(
                email = email,
                onEmailVerificationComplete = {
                    appNavController.navigate(AppNavGraph.AuthNavGraph.Login) {
                        popUpTo(AppNavGraph.AuthNavGraph.EmailVerification("")) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable<AppNavGraph.AuthNavGraph.ForgotPassword> {
            ForgotPasswordScreen(
                onOTPSent = {email->
                    appNavController.navigate(
                        AppNavGraph.AuthNavGraph.ResetPassword(email)
                    )
                }
            )
        }
        composable<AppNavGraph.AuthNavGraph.ResetPassword> {backStackEntry->
            val email = backStackEntry.toRoute<AppNavGraph.AuthNavGraph.ResetPassword>().email
            ResetPasswordScreen(
                modifier = Modifier
            )
        }
        composable<AppNavGraph.AuthNavGraph.TermsAndConditions> {
            TermsAndConditionsScreen()
        }
    }
}