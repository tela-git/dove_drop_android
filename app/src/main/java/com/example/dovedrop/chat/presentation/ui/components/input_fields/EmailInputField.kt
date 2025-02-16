package com.example.dovedrop.chat.presentation.ui.components.input_fields

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dovedrop.R
import com.example.dovedrop.chat.utils.emailRegex
import java.lang.Error

@Composable
fun EmailInputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    isError: Boolean,
    imeAction: ImeAction = ImeAction.Next
) {
    val isVerified = emailRegex.matches(value)
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier
    ) {
        Text(
            text = "Email",
            modifier = Modifier
                .align(Alignment.Start),
            style = MaterialTheme.typography.labelLarge
        )
        OutlinedTextField(
            value = value,
            onValueChange = {onValueChange(it.trimStart()) },
            enabled = enabled,
            placeholder = {
                Text(
                    text = "Enter your email address",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.6f)
                    )
                )
            },
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = imeAction
            ),
            modifier = Modifier
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                errorBorderColor = Color(0xFFED1010),
                errorTextColor = Color(0xFFED1010),
                errorCursorColor = Color(0xFFED1010),
                focusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f),
            ),
            shape = RoundedCornerShape(16.dp),
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.email_icon),
                    contentDescription = "email",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .size(20.dp)
                )
            },
            trailingIcon = {
                if (isVerified) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.check),
                        contentDescription = "password verified",
                        tint = Color(0xFF0C9409),
                        modifier = Modifier
                            .size(18.dp)
                    )
                } else if (isError) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.warning_circle),
                        contentDescription = null,
                        tint = Color(0xFFED1010),
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
            },
            singleLine = true,
            isError = isError,
        )
        if (!isVerified && value.isNotEmpty()) {
            Text(
                text = "* please enter valid email address",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFFED1010)
                ),
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}