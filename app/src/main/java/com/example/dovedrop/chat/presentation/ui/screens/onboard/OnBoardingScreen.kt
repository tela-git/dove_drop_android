package com.example.dovedrop.chat.presentation.ui.screens.onboard

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dovedrop.R
import com.example.dovedrop.chat.presentation.navigation.AppScreens
import com.example.dovedrop.chat.presentation.ui.screens.auth.AuthViewModel

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val authState by authViewModel.authState.collectAsState()

    var currentImage by remember { mutableIntStateOf(0) }
    LaunchedEffect(authState) {
        if(authState) {
            navController.navigate(AppScreens.ChatList.route) {
                popUpTo(AppScreens.ChatList.route) { inclusive = false }
            }
        }
    }

    BackHandler { if(currentImage != 0) currentImage-- }
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Dove Drop",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.height(40.dp))
        Image(
            painter =
            if(currentImage == 0) painterResource(R.drawable.onboarding_one) else painterResource(R.drawable.onboarding_two),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(322.dp, 324.dp)
        )
        Text(
            text = if(currentImage == 0)
            stringResource(R.string.onboarding_one_message) else stringResource(R.string.onboarding_two_message),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(horizontal =20.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = if(currentImage == 1) stringResource(R.string.onboarding_two_message_tag) else " ",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(30.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .size(12.dp, 12.dp)
                    .background(
                        color = if(currentImage == 0) MaterialTheme.colorScheme.onPrimaryContainer else {
                            MaterialTheme.colorScheme.secondaryContainer
                        }
                    )

            )
            Spacer(Modifier.width(5.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .size(12.dp, 12.dp)
                    .background(
                        color = if(currentImage == 1) MaterialTheme.colorScheme.onPrimaryContainer else {
                            MaterialTheme.colorScheme.secondaryContainer
                        }
                    )

            )
        }
        Spacer(Modifier.height(60.dp))
        Button(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .sizeIn(minHeight = 50.dp),
            onClick = {
                if(currentImage != 1) {
                    currentImage++
                } else {
                    navController.navigate(AppScreens.Login.route) {
                        popUpTo(AppScreens.Login.route) { inclusive = false }
                    }
                }
            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Text(
                text = if(currentImage==0) "NEXT" else "CONTINUE",
                fontSize = 20.sp,
            )
        }
    }

}
