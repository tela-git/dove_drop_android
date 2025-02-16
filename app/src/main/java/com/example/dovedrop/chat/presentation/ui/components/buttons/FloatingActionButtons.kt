package com.example.dovedrop.chat.presentation.ui.components.buttons

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.dovedrop.R

@Composable
fun AppFloatingActionButton(modifier: Modifier = Modifier) {
    FloatingActionButton(
        onClick = { }
    ) {

    }
}

@Composable
fun ChatFloatingActionButton(
    onChatIconClick: () -> Unit
) {
    FloatingActionButton(
        onClick = {
            onChatIconClick()
        },
    ) {
        Icon(
            painter = painterResource(R.drawable.add_new_chat),
            contentDescription = "Create new chat",
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}