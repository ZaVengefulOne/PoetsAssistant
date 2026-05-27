package com.example.composejoyride.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

var LocalTheme = mutableStateOf(false)

private val DarkColorScheme = darkColorScheme(
    primary = DarkBright,
    onPrimary = DarkDeep,
    primaryContainer = DarkMid,
    onPrimaryContainer = DarkBright,
    secondary = DarkMid,
    onSecondary = DarkBright,
    secondaryContainer = Color(0xFF2A6580),
    onSecondaryContainer = DarkSoft,
    tertiary = White,
    onTertiary = DarkDeep,
    background = DarkDeep,
    onBackground = White,
    surface = DarkMid,
    onSurface = DarkBright,
    surfaceVariant = Color(0xFF124F6F),
    onSurfaceVariant = DarkSoft,
    error = Red,
    onError = DarkDeep,
    scrim = Color(0xFF000000),
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    secondary = LightSecondary,
    tertiary = LightTertiary,
    background = LightBackground,
    error = Red,
    surface = LightSecondary,
    onPrimary = LightTertiary,
    onSecondary = LightTertiary,
    onTertiary = LightPrimary,
    onBackground = LightTertiary,
    onSurface = LightTertiary,
    surfaceVariant = LightBackground,
    onSurfaceVariant = LightTertiary,
    scrim = Color(0xFF000000),
)

@Composable
fun ComposeJoyrideTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (!LocalTheme.value) DarkColorScheme else LightColorScheme,
        typography = Typography,
    ) {
        VengTextFieldTheme(content = content)
    }
}
