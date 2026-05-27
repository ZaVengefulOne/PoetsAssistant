package com.example.composejoyride.ui.theme.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.composejoyride.ui.theme.liquid.LiquidGlassSupport
import com.example.composejoyride.ui.theme.liquid.liquidGlassSurfaceTint
import com.example.composejoyride.ui.theme.liquid.rememberIsolatedLiquidBackdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy

@Composable
fun VengFab(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    useLiquid: Boolean = LiquidGlassSupport.enabled,
    containerColor: Color = MaterialTheme.colorScheme.secondary,
    contentColor: Color = MaterialTheme.colorScheme.primary,
    content: @Composable () -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme

    if (useLiquid && LiquidGlassSupport.enabled) {
        val fabBackdrop = rememberIsolatedLiquidBackdrop(
            tintColor = containerColor,
        )
        val surfaceTint = liquidGlassSurfaceTint()
        val shadowColor = colorScheme.scrim.copy(alpha = 0.35f)

        Box(
            modifier = modifier
                .size(56.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = CircleShape,
                    clip = false,
                    ambientColor = shadowColor,
                    spotColor = shadowColor,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .drawBackdrop(
                        backdrop = fabBackdrop,
                        shape = { CircleShape },
                        effects = {
                            vibrancy()
                            blur(4f.dp.toPx())
                            lens(16f.dp.toPx(), 32f.dp.toPx())
                        },
                        onDrawSurface = { drawRect(surfaceTint) },
                    )
                    .clickable(role = Role.Button, onClick = onClick),
                contentAlignment = Alignment.Center,
                content = { content() },
            )
        }
    } else {
        FloatingActionButton(
            onClick = onClick,
            modifier = modifier,
            shape = CircleShape,
            containerColor = containerColor,
            contentColor = contentColor,
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp),
            content = content,
        )
    }
}
