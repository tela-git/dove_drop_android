package com.example.dovedrop.chat.presentation.ui.components.app_bars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier
) {
    BottomAppBar(
    ) {
        NavigationBar(

        ) {
            NavigationBarItem(
                selected = true,
                onClick = { },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = "Home",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            )
            NavigationBarItem(
                selected = false,
                onClick = { },
                icon = {
                    Icon(
                        imageVector = Icons.Outlined.Call,
                        contentDescription = "Calls",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            )
        }
    }


}