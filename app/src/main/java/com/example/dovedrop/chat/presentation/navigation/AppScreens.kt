package com.example.dovedrop.chat.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class AppNavGraph {
    @Serializable data object AuthNavGraph: AppNavGraph() {
        @Serializable data object OnBoarding : AppNavGraph()
        @Serializable data object Login : AppNavGraph()
        @Serializable data object Signup : AppNavGraph()
        @Serializable data object ForgotPassword: AppNavGraph()
        @Serializable data object ResetPassword: AppNavGraph()
        @Serializable data class EmailVerification(val email: String): AppNavGraph()
        @Serializable data object TermsAndConditions: AppNavGraph()
    }

    @Serializable data object MainNavGraph: AppNavGraph() {
        @Serializable data object Home : AppNavGraph()
    }
}
