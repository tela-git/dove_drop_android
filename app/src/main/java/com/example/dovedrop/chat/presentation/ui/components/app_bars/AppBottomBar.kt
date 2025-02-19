package com.example.dovedrop.chat.presentation.ui.components.app_bars

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.dovedrop.R
import com.example.dovedrop.chat.presentation.navigation.AppNavGraph

@Composable
fun AppBottomBar(
    currentDestination: NavDestination?,
    onBottomNavItemClick: (AppNavGraph) -> Unit
) {
    val insets = WindowInsets.navigationBars.asPaddingValues()

    HorizontalDivider(thickness = 1.dp, color = Color(0xFF323232))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp + insets.calculateBottomPadding())
            .navigationBarsPadding(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        topLevelRoutes.forEach { topLevelRoute ->
            val isSelected =
                currentDestination?.hierarchy?.any { it.hasRoute(topLevelRoute.route::class) } == true
            Button(
                onClick = {
                    if (!isSelected)
                        onBottomNavItemClick(topLevelRoute.route)
                },
                modifier = Modifier
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                contentPadding = PaddingValues(6.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(
                            if (isSelected) topLevelRoute.selectedIcon else topLevelRoute.unSelectedIcon
                        ),
                        contentDescription = topLevelRoute.displayName,
                        tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Unspecified,
                        modifier = Modifier
                            .size(24.dp)
                    )
                    Text(
                        text = topLevelRoute.displayName,
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = if(isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            }
        }
    }
}


val topLevelRoutes = listOf(
    TopLevelRoute("Chats", R.drawable.outline_chat_room, R.drawable.filled_chat_room, AppNavGraph.MainNavGraph.Home),
    TopLevelRoute("Calls", R.drawable.outlined_call, R.drawable.filled_call, AppNavGraph.MainNavGraph.Calls),
)

data class TopLevelRoute<T: Any>(
    val displayName: String,
    @DrawableRes val unSelectedIcon: Int,
    @DrawableRes val selectedIcon: Int,
    val route: T
)