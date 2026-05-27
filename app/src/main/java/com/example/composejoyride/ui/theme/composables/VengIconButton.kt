package com.example.composejoyride.ui.theme.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.composejoyride.ui.theme.liquid.LiquidGlassSupport
import com.example.composejoyride.ui.theme.liquid.liquidGlassSurfaceTint
import com.example.composejoyride.ui.theme.liquid.rememberIsolatedLiquidBackdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy

@Composable
fun VengIconButton(
    onClick: () -> Unit,
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Dp = 64.dp,
    useLiquid: Boolean = LiquidGlassSupport.enabled,
    containerColor: Color = MaterialTheme.colorScheme.secondary,
    contentColor: Color = MaterialTheme.colorScheme.primary,
) {
    val colorScheme = MaterialTheme.colorScheme

    if (useLiquid && LiquidGlassSupport.enabled) {
        val buttonBackdrop = rememberIsolatedLiquidBackdrop(tintColor = containerColor)
        val surfaceTint = liquidGlassSurfaceTint()
        val shadowColor = colorScheme.scrim.copy(alpha = 0.3f)

        Box(
            modifier = modifier
                .size(size)
                .shadow(
                    elevation = 8.dp,
                    shape = CircleShape,
                    clip = false,
                    ambientColor = shadowColor,
                    spotColor = shadowColor,
                ),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                Modifier
                    .size(size)
                    .drawBackdrop(
                        backdrop = buttonBackdrop,
                        shape = { CircleShape },
                        effects = {
                            vibrancy()
                            blur(3f.dp.toPx())
                            lens(12f.dp.toPx(), 24f.dp.toPx())
                        },
                        onDrawSurface = { drawRect(surfaceTint) },
                    )
                    .clickable(role = Role.Button, onClick = onClick),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = imageVector,
                    contentDescription = contentDescription,
                    tint = contentColor,
                    modifier = Modifier.size(size * 0.42f),
                )
            }
        }
    } else {
        OutlinedIconButton(
            onClick = onClick,
            modifier = modifier.size(size),
            shape = CircleShape,
            border = BorderStroke(1.5.dp, contentColor),
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription,
                tint = contentColor,
            )
        }
    }
}
