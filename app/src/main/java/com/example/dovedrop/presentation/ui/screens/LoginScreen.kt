package com.example.dovedrop.presentation.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dovedrop.R
import com.example.dovedrop.presentation.navigation.AppScreens
import com.example.dovedrop.presentation.ui.components.LongButton
import com.example.dovedrop.presentation.viewmodel.AppAuthState
import com.example.dovedrop.presentation.viewmodel.AuthViewModel
import com.example.dovedrop.presentation.viewmodel.UiState

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    navController: NavController
) {
    val authState by authViewModel.authState.collectAsState()
    val uiState by authViewModel.uiState.collectAsState()
    var isLoginEnabled by remember { mutableStateOf(false) }

    BackHandler {
        // Do nothing
    }
    LaunchedEffect(authState) {
        if(
            authState.authState == AppAuthState.Authenticated
        ) {
            navController.navigate(AppScreens.ChatList.route) {
                popUpTo(AppScreens.ChatList.route) { inclusive = false }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Dove Drop",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 20.dp)
        )
        LoginBody(
            uiState = uiState,
            onPasswordChange = {
                authViewModel.changeEnterPassword(it)
                isLoginEnabled = it.isNotBlank() && it.isNotBlank()
                               },
            onEmailChange = {
                authViewModel.changeEnterEmail(it)
            isLoginEnabled = it.isNotBlank() && it.isNotBlank()
                            },
            onPasswordVisibilityChange = { authViewModel.changePasswordVisibility(it) },
            isLoginEnabled = isLoginEnabled,
            onLoginButtonClick = { authViewModel.loginUser() },
            onSignUpInsteadClick = {
                //Navigate to signup screen
            }
        )
    }
}

@Composable
fun LoginBody(
    modifier: Modifier = Modifier,
    uiState: UiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibilityChange: (Boolean) -> Unit,
    isLoginEnabled: Boolean,
    onSignUpInsteadClick: () -> Unit,
    onLoginButtonClick: () ->  Unit
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        Column(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Email Address",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .align(Alignment.Start)
            )
            EmailInputField(
                emailAddress = uiState.email,
                onEmailChange = onEmailChange
            )
        }
        Column(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Password",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .align(Alignment.Start)
            )
            PassWordInputField(
                password = uiState.password,
                onPasswordChange =  onPasswordChange,
                isPasswordVisible = uiState.isPasswordVisible,
                onPasswordVisibilityChange = onPasswordVisibilityChange
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = modifier
                    .fillMaxWidth()
            ){
                TextButton(
                    onClick = { }
                ) {
                    Text(
                        text = "Forgot password?",
                        style = MaterialTheme.typography.labelLarge,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ){
        LongButton(
            text = "LOGIN",
            onClick = {
                onLoginButtonClick()
            },
            modifier = modifier,
            isEnabled = isLoginEnabled
        )
        TextButton(
            onClick = {
                onSignUpInsteadClick()
            }
        ) {
            Row()
            {
                Text(
                    text = "Don't have an account ?",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textDecoration = TextDecoration.Underline
                    )
                Text(
                    text = " Sign Up ",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
    Spacer(Modifier.height(10.dp))
}

@Composable
fun EmailInputField(
    modifier: Modifier = Modifier,
    emailAddress: String,
    onEmailChange: (String) -> Unit
) {
    OutlinedTextField(
        value = emailAddress,
        onValueChange = { onEmailChange(it) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
            errorBorderColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth(),
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Email,
                contentDescription = "enter your email address",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        },
        placeholder = {
            Text(
                text = "Enter Email Address",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        singleLine = true,
    )
}

@Composable
fun PassWordInputField(
    modifier: Modifier = Modifier,
    password: String,
    onPasswordChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onPasswordVisibilityChange: (Boolean) -> Unit
) {
    OutlinedTextField(
        value = password,
        onValueChange = { onPasswordChange(it) },
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
            errorBorderColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier
            .fillMaxWidth(),
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Lock,
                contentDescription = "enter your password",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    onPasswordVisibilityChange(!isPasswordVisible)
                }
            ) {
                Icon(
                    painter = if (isPasswordVisible) painterResource(R.drawable.visible) else painterResource(
                        R.drawable.not_visible
                    ),
                    contentDescription = "hide password"
                )
            }
        },
        placeholder = {
            Text(
                text = "Enter Password",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        visualTransformation = if(isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        singleLine = true,
    )
}