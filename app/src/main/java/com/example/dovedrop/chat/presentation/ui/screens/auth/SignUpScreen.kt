package com.example.dovedrop.chat.presentation.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.dovedrop.chat.presentation.ui.components.buttons.LongButtonPrimary
import com.example.dovedrop.chat.presentation.ui.components.input_fields.EmailInputField
import com.example.dovedrop.chat.presentation.ui.components.input_fields.NameInputField
import com.example.dovedrop.chat.presentation.ui.components.input_fields.PassWordInputField
import com.example.dovedrop.chat.presentation.ui.components.loading.LoadingPage
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onGoToLoginClick: () -> Unit,
    onTAndCClick: () -> Unit,
    onSignUpComplete: (email: String) -> Unit,
) {
    val viewModel : AuthViewModel = koinViewModel()
    val signUpUIData by viewModel.signUpUIData.collectAsState()
    val context = LocalContext.current
    val localFocusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.toastFlow.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
    LaunchedEffect(Unit) {
        viewModel.signUpUIData.collect { state->
            if(state.isSignUpSuccess) {
                onSignUpComplete(signUpUIData.email)
            }
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
    ) { innerPadding->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(4.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(Modifier.height(36.dp))
            Text(
                text = "Create Account",
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
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    NameInputField(
                        name = signUpUIData.name,
                        onNameChange = viewModel::updateEnteredNameSignUp,
                        modifier = Modifier,
                    )
                    if(signUpUIData.name.length in (1..<3)) {
                        Text(
                            text = "* name must be longer than 3 characters!",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFFED1010)
                            ),
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }
                EmailInputField(
                    value = signUpUIData.email,
                    onValueChange = viewModel::updateEnteredEmailSignUp,
                    isError = false,
                )
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    PassWordInputField(
                        password = signUpUIData.password,
                        onPasswordChange = viewModel::updateEnteredPasswordSignUp,
                        isPasswordVisible = signUpUIData.passwordVisibility,
                        onPasswordVisibilityChange = viewModel::changePasswordVisibility,
                        imeAction = ImeAction.Done
                    )
                    if(signUpUIData.password.length in (1..5)) {
                        Text(
                            text = "* passwords must be longer than 6 characters!",
                            style = MaterialTheme.typography.bodySmall.copy(
                                textAlign = TextAlign.Start,
                                color = Color(0xFFED1010)
                            ),
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Terms&Conditions, Privacy Policy",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .clickable { onTAndCClick() }
                )
            }
            Column(
                modifier = Modifier
                    .widthIn(max = 480.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                LongButtonPrimary(
                    onClick = {
                        localFocusManager.clearFocus()
                        viewModel.signUpUser()
                    },
                    text = "Signup",
                    isEnabled = signUpUIData.buttonEnabled
                )
            }
            Row(
                modifier = Modifier
                    .widthIn(max = 480.dp)
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account?",
                    style = MaterialTheme.typography.bodyMedium
                )
                TextButton(
                    onClick = onGoToLoginClick,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Login",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        if(signUpUIData.isLoading) {
            LoadingPage()
        }
    }
}
