package com.example.composejoyride.ui.theme.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.composejoyride.ui.theme.liquid.GlassListItem
import com.example.composejoyride.ui.theme.liquid.LiquidGlassSupport
import com.example.composejoyride.ui.theme.liquid.liquidGlassSurfaceTint
import com.example.composejoyride.ui.theme.liquid.rememberIsolatedLiquidBackdrop

@Composable
fun VengListItem(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    shape: Shape = RoundedCornerShape(16.dp),
    useLiquid: Boolean = LiquidGlassSupport.enabled,
    content: @Composable RowScope.() -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme

    if (useLiquid) {
        val backdrop = rememberIsolatedLiquidBackdrop()
        GlassListItem(
            backdrop = backdrop,
            modifier = modifier,
            onClick = onClick,
            shape = shape,
            surfaceTint = liquidGlassSurfaceTint(),
            content = content,
        )
    } else if (onClick != null) {
        Card(
            onClick = onClick,
            modifier = modifier,
            shape = shape,
            colors = CardDefaults.cardColors(containerColor = colorScheme.secondary),
            border = BorderStroke(1.dp, colorScheme.primary),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        ) {
            Row(Modifier.padding(horizontal = 12.dp, vertical = 8.dp), content = content)
        }
    } else {
        Card(
            modifier = modifier,
            shape = shape,
            colors = CardDefaults.cardColors(containerColor = colorScheme.secondary),
            border = BorderStroke(1.dp, colorScheme.primary),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        ) {
            Row(Modifier.padding(horizontal = 12.dp, vertical = 8.dp), content = content)
        }
    }
}
