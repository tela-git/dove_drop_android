package com.example.dovedrop.presentation.navigation

sealed class AppScreens(val route: String) {
    data object ChatList: AppScreens(route = "ChatList")
    data object ChatDetail: AppScreens(route = "ChatDetail")
    data object CallList: AppScreens(route = "CallList")
    data object Login: AppScreens(route = "Login")
    data object SignUp: AppScreens(route = "SignUp")
    data object PasswordForgot: AppScreens(route = "ForgotPassword")
    data object OnBoarding: AppScreens(route = "OnBoarding")
}