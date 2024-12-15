package com.lotusinc.transportmanagmentsystem.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

private val DarkColorPalette = darkColorScheme(
    primary = Color(0xFF90CAF9),  // Light Blue
    secondary = Color(0xFF42A5F5), // Deeper Blue for accents
    background = Color(0xFF121212), // Dark background
    surface = Color(0xFF1E1E1E), // Elevated surfaces
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.LightGray,
    onSurface = Color.White
)

@Composable
fun TransportAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorPalette,
        typography = Typography, // Use default or modify as needed
        content = content
    )
}

@Composable
fun TransportManagmentSystemTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorPalette

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}