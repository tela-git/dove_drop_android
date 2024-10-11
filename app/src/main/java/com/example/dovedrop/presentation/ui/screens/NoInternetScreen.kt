package com.example.dovedrop.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dovedrop.R
import com.example.dovedrop.presentation.navigation.AppScreens
import com.example.dovedrop.presentation.viewmodel.AuthViewModel

@Composable
fun NoInternetScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    navController: NavController,
    isNetworkConnected: Boolean
) {
    LaunchedEffect(isNetworkConnected) {
        if(isNetworkConnected){
            navController.navigate(AppScreens.OnBoarding.route) {
                popUpTo(AppScreens.OnBoarding.route) { inclusive = true }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.no_connection),
            contentDescription = "No Internet Connection",
            modifier = Modifier
                .size(200.dp, 200.dp)
        )
        Spacer(Modifier.height(20.dp))
        Text(
            text = "OOPS !",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(10.dp))
        Text(
            text = "You are offline",
            fontSize = 20.sp
        )
        Spacer(Modifier.height(20.dp))
        Text(
            text = "Please check your wifi router, mobile data connection and try again.",
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(20.dp))
    }
}