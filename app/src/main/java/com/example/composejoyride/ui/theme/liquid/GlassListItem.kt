package com.example.composejoyride.ui.theme.liquid

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.kyant.backdrop.Backdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy

@Composable
fun GlassListItem(
    backdrop: Backdrop,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit
) {
    val baseModifier = Modifier
        .drawBackdrop(
            backdrop = backdrop,
            shape = { RoundedCornerShape(16f.dp) },
            effects = {
                vibrancy()
                blur(3f.dp.toPx())
                lens(12f.dp.toPx(), 24f.dp.toPx())
            },
            onDrawSurface = { drawRect(Color.White.copy(alpha = 0.15f)) }
        )
        .fillMaxWidth()
        .padding(vertical = 6f.dp, horizontal = 4f.dp)

    Row(
        modifier = if (onClick != null) {
            baseModifier
                .clickable(role = Role.Button, onClick = onClick)
                .then(modifier)
        } else {
            baseModifier.then(modifier)
        },
        content = content
    )
}
