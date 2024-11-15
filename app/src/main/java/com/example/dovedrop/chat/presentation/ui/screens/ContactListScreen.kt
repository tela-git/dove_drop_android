package com.example.dovedrop.chat.presentation.ui.screens

import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dovedrop.chat.presentation.viewmodel.ContactsList
import com.example.dovedrop.chat.presentation.viewmodel.UiContact

@Composable
fun ContactListScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    contactListUiState: ContactsList
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            contactListUiState.contacts.forEach { contact->
                ContactCard(
                    contact = contact
                )
            }
        }
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    }

}

@Composable
fun ContactCard(
    contact: UiContact
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = contact.name,
                fontSize = 20.sp,
                textAlign = TextAlign.Start
            )
            Text(
                text = contact.email,
                fontSize = 20.sp
            )
        }
    }
}