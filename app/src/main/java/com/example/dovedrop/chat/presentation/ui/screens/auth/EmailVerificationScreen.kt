package com.example.dovedrop.chat.presentation.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dovedrop.chat.presentation.ui.components.buttons.LongButtonPrimary
import com.example.dovedrop.chat.presentation.ui.components.loading.LoadingPage
import com.example.dovedrop.chat.presentation.ui.components.otp.OtpScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EmailVerificationScreen(
    email: String,
    onEmailVerificationComplete: () -> Unit,
) {
    val viewModel: AuthViewModel = koinViewModel()
    val uiState by viewModel.emailVerificationUIData.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState(10)
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.toastFlowEV.collect {message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.emailVerificationUIData.collect {state->
            if(state.isEmailVerified) {
                onEmailVerificationComplete()
            }
        }
    }

    Scaffold(
    ) { innerPadding->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(Modifier.height(36.dp))
            Text(
                text = "Email Verification",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                text = "An OTP has been sent to your email $email.",
                style = MaterialTheme.typography.bodyMedium
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "One Time Password",
                    style = MaterialTheme.typography.labelLarge
                )
                OtpScreen(
                    onOtpComplete = viewModel::updateEnteredOTP,
                    onOtpChange = viewModel::updateEnteredOTP
                )
            }
            Spacer(Modifier.height(100.dp))
            LongButtonPrimary(
                text = "Verify",
                onClick = {
                    focusManager.clearFocus()
                    viewModel.verifyEmailOTP(
                        email = email
                    )
                },
                isEnabled = uiState.otp.length == 6
            )
        }
        if(uiState.isLoading) {
            LoadingPage()
        }
    }
}