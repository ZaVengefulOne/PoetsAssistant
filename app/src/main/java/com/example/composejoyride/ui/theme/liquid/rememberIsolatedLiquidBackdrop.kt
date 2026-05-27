package com.example.composejoyride.ui.theme.liquid

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kyant.backdrop.Backdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop

@Composable
fun rememberIsolatedLiquidBackdrop(
    baseColor: Color = MaterialTheme.colorScheme.background,
    tintColor: Color = MaterialTheme.colorScheme.secondary,
): Backdrop = rememberLayerBackdrop {
    drawRect(baseColor)
    drawRect(tintColor.copy(alpha = 0.35f))
}

@Composable
fun liquidGlassSurfaceTint(): Color =
    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
