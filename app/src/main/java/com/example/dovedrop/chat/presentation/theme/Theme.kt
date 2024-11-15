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
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = ButtonBackgroundDark,
    secondary = ButtonTextDark,
    background = DarkBackground,
    surface = DarkBackground,
    onPrimary = ButtonTextDark,
    onSecondary = PrimaryTextDark,
    onBackground = PrimaryTextDark,
    onSurface = PrimaryTextDark,
)

private val LightColorScheme = lightColorScheme(
    primary = ButtonBackgroundLight,
    secondary = ButtonTextLight,
    background = LightBackground,
    surface = LightBackground,
    onPrimary = ButtonTextLight,
    onSecondary = PrimaryTextLight,
    onBackground = PrimaryTextLight,
    onSurface = PrimaryTextLight,
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