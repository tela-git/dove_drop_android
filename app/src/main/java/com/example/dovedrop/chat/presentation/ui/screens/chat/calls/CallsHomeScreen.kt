package com.example.dovedrop.chat.presentation.ui.screens.chat.calls

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import com.example.dovedrop.chat.presentation.navigation.AppNavGraph
import com.example.dovedrop.chat.presentation.ui.components.app_bars.AppBottomBar
import com.example.dovedrop.chat.presentation.ui.components.app_bars.CallsHomeTopBar

@Composable
fun CallsHomeScreen(
    currentDestination: NavDestination?,
    onBottomBarIconClick: (AppNavGraph) -> Unit,
) {
    Scaffold(
        topBar = {
            CallsHomeTopBar(
                onMoreOptionsClick = { },
                isNavigateBackIconVisible = false
            )
        },
        bottomBar = {
            AppBottomBar(
                currentDestination = currentDestination,
                onBottomNavItemClick = onBottomBarIconClick
            )
        }
    ) { innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Calls home screen"
            )
        }
    }
}