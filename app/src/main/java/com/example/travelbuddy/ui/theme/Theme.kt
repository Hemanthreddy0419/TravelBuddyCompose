package com.example.travelbuddy.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Explicit light colour scheme
private val LightColorScheme = lightColorScheme(
    primary = Teal700,
    onPrimary = Color.White,
    primaryContainer = Teal100,
    onPrimaryContainer = Teal900,
    secondary = Amber700,
    onSecondary = Color.White,
    secondaryContainer = Amber100,
    onSecondaryContainer = Amber900,
    background = LightBackground,
    onBackground = LightOnSurface,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
    outline = LightOutline
)

// Explicit dark colour scheme
private val DarkColorScheme = darkColorScheme(
    primary = Teal200,
    onPrimary = Teal900,
    primaryContainer = Teal800,
    onPrimaryContainer = Teal100,
    secondary = Amber200,
    onSecondary = Amber900,
    secondaryContainer = Amber900,
    onSecondaryContainer = Amber100,
    background = DarkBackground,
    onBackground = DarkOnSurface,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    outline = DarkOutline
)

/**
 * App-wide theme. It follows the system light/dark setting, so toggling dark mode
 * in the device settings switches every screen without any extra code.
 */
@Composable
fun TravelBuddyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        content = content
    )
}
