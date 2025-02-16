package com.example.dovedrop.chat.presentation.ui.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun ProfileDialogBox(
    modifier: Modifier = Modifier,
    onDialogDismiss: () -> Unit,
    onLogOutClick: () -> Unit,
    userEmail: String?
    ) {
    Dialog(
        onDismissRequest = { onDialogDismiss() }
    ) {
        Card {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Email: $userEmail"
                )
                Button(
                    onClick = { onLogOutClick() }
                ) {
                    Text(
                        text = "Log out"
                    )
                }
            }
        }
    }
}