package com.example.composejoyride.ui.theme.liquid


import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kyant.backdrop.Backdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy

@Composable
fun BoxScope.GlassBottomSheet(
    backdrop: Backdrop,
    content: @Composable ColumnScope.() -> Unit
) {
    val bottomSheetBackdrop = rememberLayerBackdrop()
    Column(
        Modifier
            .safeContentPadding()
            .drawBackdrop(
                backdrop = backdrop,
                exportedBackdrop = bottomSheetBackdrop,
                shape = { RoundedCornerShape(44f.dp) },
                effects = {
                    vibrancy()
                    blur(4f.dp.toPx())
                    lens(24f.dp.toPx(), 48f.dp.toPx(), true)
                },
                onDrawSurface = { drawRect(Color.White.copy(alpha = 0.5f)) }
            )
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
    ) {
       content()
    }
}