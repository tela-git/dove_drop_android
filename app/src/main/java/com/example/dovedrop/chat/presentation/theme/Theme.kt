package com.example.dovedrop.chat.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF7C4DFF),         // Slightly brighter purple for primary elements
    secondary = Color(0xFF00BFA6),       // Teal remains the same for consistency
    background = Color(0xFF121212),      // Dark background for dark theme
    surface = Color(0xFF1E1E1E),         // Dark gray for surfaces like cards and message bubbles

    onPrimary = Color(0xFFFFFFFF),       // White text/icons on primary color
    onSecondary = Color(0xFFFFFFFF),     // White text/icons on secondary color
    onBackground = Color(0xFFFFFFFF),    // White text/icons on dark background
    onSurface = Color(0xFFFFFFFF),       // White text/icons on dark surface

    primaryContainer = Color(0xFF3700B3), // Darker purple container for primary elements
    onPrimaryContainer = Color(0xFFFFFFFF), // White text/icons on primary container

    secondaryContainer = Color(0xFF004D40), // Darker teal container for secondary elements
    onSecondaryContainer = Color(0xFFFFFFFF), // White text/icons on secondary container
)

private val LightColorScheme = lightColorScheme(

    primary = Color(0xFF6C63FF),         // Vibrant purple for primary elements
    secondary = Color(0xFF00BFA6),       // Teal for secondary elements
    background = Color(0xFFF5F5F5),      // Light gray for the background
    surface = Color(0xFFFFFFFF),         // White for surfaces like cards and message bubbles

    onPrimary = Color(0xFFFFFFFF),       // White text/icons on primary color
    onSecondary = Color(0xFF000000),     // Black text/icons on secondary color
    onBackground = Color(0xFF333333),   // Dark gray text/icons on background
    onSurface = Color(0xFF333333),       // Dark gray text/icons on surface

    primaryContainer = Color(0xFFE0E0FF), // Light purple container for primary elements
    onPrimaryContainer = Color(0xFF1A1A1A), // Dark gray text/icons on primary container

    secondaryContainer = Color(0xFFB2F5EA), // Light teal container for secondary elements
    onSecondaryContainer = Color(0xFF1A1A1A), // Dark gray text/icons on secondary container
)




@Composable
fun DoveDropTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}