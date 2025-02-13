package com.example.dovedrop.chat.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class AppNavGraph {
    @Serializable data object AuthNavGraph: AppNavGraph() {
        @Serializable
        data object OnBoarding : AppNavGraph()
        @Serializable
        data object Login : AppNavGraph()
        @Serializable
        data object Signup : AppNavGraph()
        @Serializable
        data object ResetPassword: AppNavGraph()
    }

    @Serializable data object MainNavGraph {
        @Serializable
        data object Home : AppNavGraph()
    }
}
