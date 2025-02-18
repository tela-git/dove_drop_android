package com.example.dovedrop.chat.presentation.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dovedrop.chat.presentation.ui.components.buttons.LongButtonPrimary
import com.example.dovedrop.chat.presentation.ui.components.input_fields.EmailInputField
import com.example.dovedrop.chat.presentation.ui.components.loading.LoadingPage
import com.example.dovedrop.chat.presentation.ui.components.otp.OtpScreen
import com.example.dovedrop.chat.presentation.ui.screens.auth.reset_password.ResetPasswordViewModel
import com.example.dovedrop.chat.utils.emailRegex
import org.koin.androidx.compose.koinViewModel

@Composable
fun ForgotPasswordScreen(
    onOTPSent: (String) -> Unit,
) {
    val viewModel: ResetPasswordViewModel = koinViewModel()
    val uiState by viewModel.fpUIData.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.resetFPState()
    }

    LaunchedEffect(Unit) {
        viewModel.fpUIData.collect { state ->
            if (state.otpSent) {
                onOTPSent(uiState.email)
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.fpToastFlow.collect {message->
            Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
        }
    }
    Scaffold(

    ) { innerPadding->
        val scrollState = rememberScrollState()
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
                text = "Forgot Password",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                text = "Enter your email address to get a One Time Password.",
                style = MaterialTheme.typography.bodyMedium
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                EmailInputField(
                    value = uiState.email,
                    onValueChange = viewModel::updateEnteredEmailFP,
                    isError = false
                )
            }
            Spacer(Modifier.height(100.dp))
            LongButtonPrimary(
                text = "Send",
                onClick = {
                    viewModel.sendOTPToEmail()
                    focusManager.clearFocus()
                },
                isEnabled = uiState.email.matches(emailRegex)
            )
        }
        if(uiState.isLoading) {
            LoadingPage()
        }
    }
}