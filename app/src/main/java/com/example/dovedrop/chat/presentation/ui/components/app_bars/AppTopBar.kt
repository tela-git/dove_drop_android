package com.example.dovedrop.chat.presentation.ui.components.app_bars

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatsHomeTopBar(
    modifier: Modifier = Modifier,
    onMoreOptionsClick: () -> Unit,
    isNavigateBackIconVisible: Boolean,
    onRefresh: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Dove Drop",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            )
        },
        actions = {
            IconButton(
                onClick = { onMoreOptionsClick() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = "More options",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            IconButton(
                onClick = onRefresh
            ) {
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = ""
                )
            }
        },
        navigationIcon = {
            AnimatedVisibility(isNavigateBackIconVisible){
                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigate"
                    )
                }
            }
        },
        modifier = modifier
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CallsHomeTopBar(
    modifier: Modifier = Modifier,
    onMoreOptionsClick: () -> Unit,
    isNavigateBackIconVisible: Boolean,
) {
    TopAppBar(
        title = {
            Text(
                text = "Calls",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            )
        },
        actions = {
            IconButton(
                onClick = { onMoreOptionsClick() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = "More options",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        navigationIcon = {
            AnimatedVisibility(isNavigateBackIconVisible){
                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigate"
                    )
                }
            }
        },
        modifier = modifier
    )
}