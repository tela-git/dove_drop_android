package com.example.dovedrop.chat.presentation.ui.screens.main.chat.chat_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.KeyboardVoice
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.dovedrop.R
import com.example.dovedrop.chat.data.dummy_data.dummyMessages
import com.example.dovedrop.chat.data.model.chat.ChatMessage
import com.example.dovedrop.chat.data.model.chat.MessageStatus
import com.example.dovedrop.chat.utils.getDateAndTime
import com.example.dovedrop.chat.utils.getReadableDate
import com.example.dovedrop.chat.utils.getReadableDateTwo
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatRoomScreen(
    roomId: String,
    p2Name: String,
    p2DpUrl: String,
    onNavigateUp: () -> Unit,
) {
    val chatRoomViewModel: ChatRoomViewModel = koinViewModel()
    val uiState by chatRoomViewModel.uiState.collectAsStateWithLifecycle()
    var selectionEnabled by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            ChatRoomTopBar(
                p2Name = p2Name,
                p2DpUrl = p2DpUrl,
                onNavigateUp = onNavigateUp,
                onMoreClick = { }
            )
        },
        bottomBar = {
            ChatRoomBottomBar(
                message = uiState.text,
                onValueChange = chatRoomViewModel::updateInputMessage,
                modifier = Modifier,
                onSendClick = { }
            )
        }
    ) { innerPadding->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            reverseLayout = true
        ) {
            items(
                items = dummyMessages,
                key = { it.id }
            ) { message->
                MessageCard(
                    message = message,
                    userEmail = dummyMessages[1].sender,
                    selectionEnabled = selectionEnabled,
                    onEnableSelection = { selectionEnabled = true }
                )
            }
        }
    }
}

@Composable
private fun MessageCard(
    message: ChatMessage,
    userEmail: String,
    selectionEnabled: Boolean,
    onEnableSelection: () -> Unit
) {
    var selected by remember { mutableStateOf(false) }
    val isUserSender = userEmail == message.sender
    Row(
        modifier = Modifier
//            .combinedClickable(
//                onLongClick = { onEnableSelection() },
//                onClick = { selected = !selected}
//            )
//            .selectable(
//                selected = selected,
//                enabled = true
//            ) {
//                selected = !selected
//            }
            .fillMaxWidth()
            .padding(vertical = 8.dp)
                ,
        horizontalArrangement = if(isUserSender) Arrangement.End else Arrangement.Start
    ) {
        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = if(isUserSender)
                    MaterialTheme.colorScheme.primaryContainer else
                MaterialTheme.colorScheme.secondaryContainer
            ),
            shape = if(isUserSender) RoundedCornerShape(20.dp, 0.dp, 20.dp, 20.dp) else RoundedCornerShape(0.dp, 20.dp, 20.dp, 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 260.dp)
                    .padding(10.dp)
            ) {
                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = if(isUserSender) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    modifier = Modifier
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(Alignment.End),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = getReadableDateTwo(message.timeStamp),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 8.sp
                        )
                    )
                    Text(
                        text = getDateAndTime(message.timeStamp).second,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 8.sp
                        )
                    )
                    when (message.status) {
                        MessageStatus.Uploaded.name -> {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.one_tick),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(18.dp),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                                    alpha = 0.4f
                                )
                            )
                        }

                        MessageStatus.Delivered.name -> {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.read_tick),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(18.dp),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                                    alpha = 0.4f
                                )
                            )
                        }

                        MessageStatus.Read.name -> {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.read_tick),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(18.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
        if(selectionEnabled) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(color = if (selected) Color.Black else Color.White)
                        .border(width = 1.dp, color = MaterialTheme.colorScheme.primary)
                        .clip(CircleShape)
                        .size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun ChatRoomBottomBar(
    message: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit,
    modifier: Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = message,
            onValueChange = onValueChange,
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(20.dp)
                )
                .height(42.dp)
                .animateContentSize(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSendClick() }
            ),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    innerTextField()
                    if (message.isEmpty()) {
                        Text(
                            text = "Type some message",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(
                                    alpha = 0.7f
                                )
                            )
                        )
                    }
                }
            }
        )
        AnimatedVisibility(message.isEmpty()) {
            IconButton(
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Outlined.KeyboardVoice,
                    contentDescription = "Voice type",
                    modifier = Modifier
                )
            }
        }
        AnimatedVisibility(message.isNotBlank()) {
            IconButton(
              onClick = { }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.Send,
                    contentDescription = "Send message",
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatRoomTopBar(
    p2Name: String,
    p2DpUrl: String,
    onNavigateUp: () -> Unit,
    onMoreClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = p2Name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier
                    .padding(horizontal = 4.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { onNavigateUp() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Navigate up"
                )
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(p2DpUrl)
                            .build(),
                        contentDescription = "Display picture",
                        error = painterResource(R.drawable.user_default_dp),
                        fallback = painterResource(R.drawable.user_default_dp),
                        placeholder = painterResource(R.drawable.user_default_dp),
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        },
        modifier = Modifier
            .padding(start = 8.dp),
        actions = {
            IconButton(
                onClick = { onMoreClick() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.MoreVert,
                    contentDescription = "More Options"
                )
            }
        }
    )
}