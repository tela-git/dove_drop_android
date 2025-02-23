package com.example.dovedrop.chat.presentation.ui.screens.main.chat.chat_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.dovedrop.R
import com.example.dovedrop.chat.data.model.chat.ChatMessage
import com.example.dovedrop.chat.data.model.chat.ChatRoom
import com.example.dovedrop.chat.data.model.chat.ChatRoomType
import com.example.dovedrop.chat.data.model.chat.MessageStatus
import com.example.dovedrop.chat.presentation.theme.DoveDropTheme
import com.example.dovedrop.chat.utils.getReadableDate

@Composable
fun ChatRoomCard(
    chatRoom: ChatRoom,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(12.dp)
            //.height(68.dp)
                ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(chatRoom.participant2Dp)
                .build(),
            contentDescription = "Display picture",
            error = painterResource(R.drawable.user_default_dp),
            fallback = painterResource(R.drawable.user_default_dp),
            placeholder = painterResource(R.drawable.user_default_dp),
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
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
                    text = chatRoom.participant2Name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = getReadableDate(chatRoom.lastMessage?.timeStamp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Text(
                text = chatRoom.lastMessage?.text ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

val dummyChatRoom = ChatRoom(
    id = "chatRoomThree",
    participants = listOf("umesh1234@gmail.com", "madhavexample@gmail.com"),
    chatRoomType = ChatRoomType.Private.name,
    createdAt = 1739440064019L,
    lastMessage = ChatMessage(
        id = "messageThree",
        sender = "umesh1234@gmail.com",
        text = "Welcome to the group! slkfjls f slfkjl lskdjf sfs sdfs sddf",
        timeStamp = 1739947384000L,
        status = MessageStatus.Undelivered.name,
        chatRoomId = "chatRoomThree"
    ),
    participant2Name = "Madhav",
    participant2Dp = "https://images.unsplash.com/photo-1544723795-3fb6469f5b39?q=80&w=1978&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
)

@Preview
@Composable
private fun ChatRoomCardPreview() {
    DoveDropTheme {
        ChatRoomCard(
            chatRoom = dummyChatRoom,
            onClick = { }
        )
    }
}