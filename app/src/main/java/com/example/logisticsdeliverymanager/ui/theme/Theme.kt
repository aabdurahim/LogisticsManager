package com.example.logisticsdeliverymanager.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Navy,
    onPrimary = CardWhite,
    secondary = Orange,
    onSecondary = CardWhite,
    tertiary = SuccessGreen,
    background = BackgroundBlue,
    onBackground = TextDark,
    surface = CardWhite,
    onSurface = TextDark,
    surfaceVariant = BorderBlue,
    onSurfaceVariant = TextMuted,
    outline = BorderBlue
)

private val DarkColors = darkColorScheme(
    primary = Orange,
    onPrimary = Navy,
    secondary = NavyLight,
    onSecondary = CardWhite,
    tertiary = SuccessGreen
)

@Composable
fun LogisticsDeliveryManagerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}
