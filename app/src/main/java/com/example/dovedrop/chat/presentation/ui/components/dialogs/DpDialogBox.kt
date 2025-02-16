package com.example.dovedrop.chat.presentation.ui.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog

@Composable
fun DpDialogBox(
    onDismiss: () -> Unit,
    //chatRoomCardUI: ChatRoomCardUI,
    onDpClick: () -> Unit,
    onMessageClick: () -> Unit,
    onCallClick: () -> Unit,
    onMoreInfoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Column(

        ) {
//            Card(
//                modifier = Modifier,
//                colors = CardDefaults.cardColors().copy(
//                    containerColor = Color.Transparent,
//                    contentColor = MaterialTheme.colorScheme.primaryContainer
//                )
//            ) {
//                Text(
//                    text = chatRoomCardUI.contactName,
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Medium,
//                    modifier = Modifier
//                        .padding(horizontal = 8.dp)
//                )
//            }
//            ElevatedCard(
//                modifier = Modifier
//                    .size(260.dp, 250.dp),
//                shape = RectangleShape,
//                colors = CardDefaults.cardColors().copy(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer
//                )
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                ) {
//                    Image(
//                        painter = painterResource(R.drawable.default_dp),
//                        contentDescription = null,
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier
//                            .weight(1f)
//                            .clickable { onDpClick() }
//                    )
//                    HorizontalDivider()
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 16.dp, vertical = 8.dp),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//
//                        ) {
//                        Row(
//                            horizontalArrangement = Arrangement.spacedBy(32.dp)
//                        ) {
//                            Icon(
//                                imageVector = Icons.AutoMirrored.Filled.Message,
//                                contentDescription = stringResource(R.string.message),
//                                modifier = Modifier
//                                    .clickable { onMessageClick() }
//                            )
//                            Icon(
//                                imageVector = Icons.Default.Call,
//                                contentDescription = stringResource(R.string.voice_call),
//                                modifier = Modifier
//                                    .clickable { onCallClick() }
//                            )
//                        }
//                        Icon(
//                            imageVector = Icons.Default.Info,
//                            contentDescription = "More information about ${chatRoomCardUI.contactName}",
//                            modifier = Modifier
//                                .clickable { onMoreInfoClick() }
//                        )
//                    }
//                }
//            }
//        }
        }
    }
}