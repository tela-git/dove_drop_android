package com.example.dovedrop.presentation.ui.components

import android.service.autofill.OnClickAction
import androidx.activity.result.IntentSenderRequest
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dovedrop.presentation.navigation.AppScreens

@Composable
fun LongButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: ()-> Unit,
    isEnabled: Boolean = false
) {
    Button(
        modifier = modifier
            .padding(vertical = 20.dp)
            .fillMaxWidth()
            .sizeIn(minHeight = 50.dp),
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        enabled = isEnabled,
    ) {
        Text(
            text = text,
            fontSize = 20.sp,
        )
    }
}