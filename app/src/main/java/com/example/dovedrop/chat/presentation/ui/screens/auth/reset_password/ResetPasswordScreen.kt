package com.example.dovedrop.chat.presentation.ui.screens.auth.reset_password

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.dovedrop.chat.presentation.ui.components.otp.OtpScreen
import com.example.dovedrop.chat.presentation.ui.components.buttons.LongButtonPrimary
import com.example.dovedrop.chat.presentation.ui.components.input_fields.PassWordInputField
import org.koin.androidx.compose.koinViewModel

@Composable
fun ResetPasswordScreen(
    modifier: Modifier = Modifier,
    email: String,
    onPasswordResetSuccess: () -> Unit,
) {
    val viewModel: ResetPasswordViewModel = koinViewModel()
    val rpUIData by viewModel.rpUiData.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.rpUiData.collect{state->
            if(state.isPasswordResetSuccess) {
                onPasswordResetSuccess()
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.rpToastFlow.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
    ) { innerPadding->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(4.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(Modifier.height(36.dp))
            Text(
                text = "Reset Password",
                style = MaterialTheme.typography.titleLarge.copy(
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Column(
                modifier = Modifier
                    .widthIn(max = 480.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Spacer(Modifier.height(24.dp))
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
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PassWordInputField(
                        title = "Password",
                        password = rpUIData.passwordOne,
                        onPasswordChange = viewModel::updateEnteredPasswordOne,
                        isPasswordVisible = rpUIData.passwordOneVisibility,
                        onPasswordVisibilityChange = viewModel::changePasswordOneVisibility
                    )
                    Spacer(Modifier.height(24.dp))
                    PassWordInputField(
                        title = "Re-enter Password",
                        password = rpUIData.passwordTwo,
                        onPasswordChange = viewModel::updateEnteredPasswordTwo,
                        isPasswordVisible = rpUIData.passwordTwoVisibility,
                        onPasswordVisibilityChange = viewModel::changePasswordTwoVisibility,
                        imeAction = ImeAction.Done
                    )
                    Spacer(Modifier.height(8.dp))
                    Column {
                        if (rpUIData.passwordOne != rpUIData.passwordTwo) {
                            Text(
                                text = "* passwords do not match!",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    textAlign = TextAlign.Start,
                                    color = Color(0xFFED1010)
                                ),
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                        if(rpUIData.passwordOne.length in (1..5) || rpUIData.passwordTwo.length in (1..5)) {
                            Text(
                                text = "* password must be longer than 6 characters!",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    textAlign = TextAlign.Start,
                                    color = Color(0xFFED1010)
                                ),
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .widthIn(max = 480.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Resend Password",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .clickable {
                            //onResendPasswordClick
                        }
                        .padding(horizontal = 4.dp)
                )
            }
            Column(
                modifier = Modifier
                    .widthIn(max = 480.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LongButtonPrimary(
                    onClick = {
                        viewModel.updatePassword(email)
                    },
                    text = "Update Password",
                    isEnabled = rpUIData.buttonEnabled
                )
            }
        }
    }
}