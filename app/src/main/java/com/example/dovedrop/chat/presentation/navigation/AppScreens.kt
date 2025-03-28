package com.example.dovedrop.chat.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class AppNavGraph {
    @Serializable data object AuthNavGraph: AppNavGraph() {
        @Serializable data object OnBoarding : AppNavGraph()
        @Serializable data object Login : AppNavGraph()
        @Serializable data object Signup : AppNavGraph()
        @Serializable data object ForgotPassword: AppNavGraph()
        @Serializable data class ResetPassword(val email: String): AppNavGraph()
        @Serializable data class EmailVerification(val email: String): AppNavGraph()
        @Serializable data object TermsAndConditions: AppNavGraph()
    }

    @Serializable data object MainNavGraph: AppNavGraph() {
        @Serializable data object Home : AppNavGraph()
        @Serializable data class ChatRoom(
            val id: String,
            val p2Name: String,
            val p2Dp: String
        ) : AppNavGraph()
        @Serializable data object Calls: AppNavGraph()
        @Serializable data object SettingsHome: AppNavGraph()
        @Serializable data object AddChatRoom: AppNavGraph()
    }
}
