package com.example.composejoyride.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

var LocalTheme = mutableStateOf(false)
private val DarkColorScheme = darkColorScheme(
    primary = Dark,
    secondary = DarkCyan,
    tertiary = White,
    background = DarkerCyan,
    error = Red,
    surface = DarkerCyan
)
private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    secondary = LightSecondary,
    tertiary = LightTertiary,
    background = LightBackground,
    error = Red,
    surface = LightBackground
)
private val NewColorScheme = darkColorScheme(
    primary = Color(0xFF2f3d83),
    secondary = Color(0xFF3D6CA9),
    tertiary = Color(0xFFF5F6F9),
    background = Color(0xFF2f3d83),
    error = Red,
    surface = DarkerCyan
)

@Composable
fun ComposeJoyrideTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = if (!LocalTheme.value) /*DarkColorScheme*/ NewColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}