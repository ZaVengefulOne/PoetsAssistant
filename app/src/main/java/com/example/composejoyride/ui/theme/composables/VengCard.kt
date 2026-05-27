package com.example.composejoyride.ui.theme.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.composejoyride.ui.theme.liquid.GlassCard
import com.example.composejoyride.ui.theme.liquid.LiquidGlassSupport
import com.example.composejoyride.ui.theme.liquid.liquidGlassSurfaceTint
import com.example.composejoyride.ui.theme.liquid.rememberIsolatedLiquidBackdrop

@Composable
fun VengCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    shape: Shape = RoundedCornerShape(12.dp),
    contentPadding: Dp = 16.dp,
    useLiquid: Boolean = LiquidGlassSupport.enabled,
    content: @Composable BoxScope.() -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme
    val clickableModifier = if (onClick != null) {
        modifier.clickable(onClick = onClick)
    } else {
        modifier
    }

    if (useLiquid) {
        val backdrop = rememberIsolatedLiquidBackdrop()
        GlassCard(
            backdrop = backdrop,
            modifier = clickableModifier,
            shape = shape,
            surfaceTint = liquidGlassSurfaceTint(),
            contentPadding = contentPadding,
            content = content,
        )
    } else if (onClick != null) {
        Card(
            onClick = onClick,
            modifier = modifier,
            shape = shape,
            colors = CardDefaults.cardColors(containerColor = colorScheme.secondary),
            border = BorderStroke(1.dp, colorScheme.primary),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        ) {
            Box(Modifier.padding(contentPadding), content = content)
        }
    } else {
        Card(
            modifier = modifier,
            shape = shape,
            colors = CardDefaults.cardColors(containerColor = colorScheme.secondary),
            border = BorderStroke(1.dp, colorScheme.primary),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
        ) {
            Box(Modifier.padding(contentPadding), content = content)
        }
    }
}
