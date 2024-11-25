package com.example.dovedrop.chat.presentation.navigation

sealed class AppScreens(val route: String) {
    data object ChatList: AppScreens(route = "ChatRoomsList")
    data object ChatDetail: AppScreens(route = "ChatDetail")
    data object CallList: AppScreens(route = "CallList")
    data object Login: AppScreens(route = "Login")
    data object SignUp: AppScreens(route = "SignUp")
    data object PasswordForgot: AppScreens(route = "ForgotPassword")
    data object OnBoarding: AppScreens(route = "OnBoarding")
    data object NoInternet: AppScreens(route = "NoInternet")
    data object ContactList: AppScreens(route = "ContactList")
}