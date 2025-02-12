package com.example.dovedrop.chat.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class MainNavGraph {
    @Serializable data object OnBoarding: MainNavGraph()
    @Serializable data object Login: MainNavGraph()
    @Serializable data object Signup: MainNavGraph()

    @Serializable data object  Home: MainNavGraph()
}
