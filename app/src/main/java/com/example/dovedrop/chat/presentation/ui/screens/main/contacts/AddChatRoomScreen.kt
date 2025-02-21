package com.example.dovedrop.chat.presentation.ui.screens.main.contacts

import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.ChatBubble
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.dovedrop.R
import com.example.dovedrop.chat.data.model.chat.ChatRoom
import com.example.dovedrop.chat.data.model.user.ContactDTO
import com.example.dovedrop.chat.presentation.ui.components.buttons.LongButtonPrimary
import com.example.dovedrop.chat.presentation.ui.components.input_fields.EmailInputField
import com.example.dovedrop.chat.presentation.ui.components.input_fields.NameInputField
import com.example.dovedrop.chat.presentation.ui.components.loading.LoadingPage
import com.example.dovedrop.chat.utils.emailRegex
import com.example.dovedrop.chat.utils.getReadableDate
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddChatRoomScreen(
    onNavigateUp: () -> Unit,
) {
    val contactsViewModel: ContactsViewModel = koinViewModel()
    val uiState by contactsViewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        contactsViewModel.getAllContacts()
    }

    LaunchedEffect(Unit) {
        contactsViewModel.toastFlow.collect {message->
            Toast.makeText(context, message,Toast.LENGTH_SHORT).show()
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add Chat",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateUp
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Navigate Up",
                        )
                    }
                }
            )
        }
    ) { innerPadding->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                AddNewContactButton(
                    onClick = {
                        contactsViewModel.updateSheetVisibility()
                    }
                )
            }
            items(
                items = uiState.contacts ?: emptyList(),
            ) {contact->
                ContactCard(
                    contact = contact,
                    onClick = { },
                    onInviteClick = { }
                )
            }

        }
        AnimatedVisibility(uiState.isBottomSheetVisible){
            ModalBottomSheet(
                onDismissRequest = {
                    scope.launch {
                        contactsViewModel.updateSheetVisibility()
                        sheetState.hide()
                    }
                    contactsViewModel.clearEntries()
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Add New Contact",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Dismiss",
                            modifier = Modifier
                                .clip(CircleShape)
                                .clickable {
                                    scope.launch {
                                        contactsViewModel.updateSheetVisibility()
                                        sheetState.hide()
                                        contactsViewModel.clearEntries()
                                    }
                                }
                        )
                    }
                    NameInputField(
                        name = uiState.contactName,
                        onNameChange = contactsViewModel::updateInputName,
                        modifier = Modifier,
                        placeHolderText = "Enter name"
                    )
                    EmailInputField(
                        value = uiState.contactEmail,
                        onValueChange = contactsViewModel::updateInputEmail,
                        isError = false,
                        imeAction = ImeAction.Done,
                        placeholderText = "Enter email address"
                    )
                    Spacer(Modifier.height(20.dp))
                    LongButtonPrimary(
                        text = "Add Contact",
                        onClick = { contactsViewModel.addContact() },
                        isEnabled = uiState.contactName.length >=3 && uiState.contactEmail.matches(emailRegex)
                    )
                    Spacer(Modifier.height(40.dp))
                }
            }
        }
    }
    if(uiState.isLoading) {
        LoadingPage()
    }
}

@Composable
private fun AddNewContactButton(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .height(48.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(44.dp)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.PersonAdd,
                contentDescription = "Add new contact",
                tint = MaterialTheme.colorScheme.primary,
            )
        }
        Text(
            text = "Add New Contact",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
        )
    }
}


@Composable
private fun ContactCard(
    contact: ContactDTO,
    onClick: () -> Unit,
    onInviteClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
            //.height(68.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(contact.p2DpUrl)
                    .build(),
                contentDescription = "Display picture",
                error = painterResource(R.drawable.user_default_dp),
                fallback = painterResource(R.drawable.user_default_dp),
                placeholder = painterResource(R.drawable.user_default_dp),
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = contact.p2Name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Normal
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                text = contact.availability,
                style = MaterialTheme.typography.bodySmall
            )
        }
        if(!contact.hasAccount) {
            TextButton(
                onClick = onInviteClick
            ){
                Text(
                    text = "Invite",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
    }
}