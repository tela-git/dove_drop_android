package com.example.dovedrop.chat.presentation.ui.screens.main.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowBack
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.example.dovedrop.R
import com.example.dovedrop.chat.data.model.user.UserAccountDetails
import com.example.dovedrop.chat.presentation.theme.DoveDropTheme
import com.example.dovedrop.chat.presentation.ui.components.buttons.LongButtonSecondary
import kotlin.math.truncate

@Composable
fun SettingsHomeScreen(
    onNavigateUp: () -> Unit,
    onLogoutClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            SettingsHomeTopAppBar(
                onNavigateUp = onNavigateUp
            )
        }
    ) { innerPadding->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            UserDetailsCard(
                userAccountDetails = defaultUserAccountDetails,
                onEditAccountDetailsClick = { }
            )
            Spacer(Modifier.height(16.dp))
            LongButtonSecondary(
                text = "Logout",
                onClick = onLogoutClick,
                isEnabled = true
            )
        }
    }
}

@Composable
private fun UserDetailsCard(
    userAccountDetails: UserAccountDetails,
    onEditAccountDetailsClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
        ,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(userAccountDetails.profilePicUrl)
                    .build(),
                contentDescription = "Profile picture",
                placeholder = painterResource(R.drawable.user_default_dp),
                fallback = painterResource(R.drawable.user_default_dp),
                error = painterResource(R.drawable.user_default_dp),
                modifier = Modifier
                    .clip(CircleShape)
                    .size(80.dp),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = userAccountDetails.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = userAccountDetails.availability,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(
                onClick = onEditAccountDetailsClick
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.edit_square),
                    contentDescription = "Edit your details"
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsHomeTopAppBar(
    onNavigateUp: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigateUp
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = ""
                )
            }
        }
    )
}

private val defaultUserAccountDetails = UserAccountDetails(
    name = "Umesh",
    email = "umesh123456@gmail.com",
    availability = "Available",
    profilePicUrl = "https://images.unsplash.com/photo-1457449940276-e8deed18bfff?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
)

@Preview
@Composable
private fun SettingsHomeScreenPreview() {
    DoveDropTheme {
        SettingsHomeScreen({}){}
    }
}