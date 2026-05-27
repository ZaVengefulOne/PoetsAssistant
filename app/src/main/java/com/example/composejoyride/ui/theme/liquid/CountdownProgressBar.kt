package com.example.composejoyride.ui.theme.liquid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kyant.backdrop.Backdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy

/**
 * Убывающий прогресс-бар в стиле liquid glass (как трек [GlassSlider]).
 * [progress] от 1f (полный) до 0f (пустой); обычно анимируется за 5 секунд.
 */
@Composable
fun CountdownProgressBar(
    progress: Float,
    backdrop: Backdrop,
    modifier: Modifier = Modifier,
    trackColor: Color = Color(0xFF2E7D32),
    fillColor: Color = Color(0xFF1B5E20),
) {
    val progressCoerced = progress.coerceIn(0f, 1f)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(14.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        // Трек (фон на всю ширину) — стиль как у GlassSlider
        Box(
            Modifier
                .matchParentSize()
                .drawBackdrop(
                    backdrop = backdrop,
                    shape = { RoundedCornerShape(5.dp) },
                    effects = {
                        vibrancy()
                        blur(2f.dp.toPx())
                        lens(8f.dp.toPx(), 16f.dp.toPx())
                    },
                    onDrawSurface = { drawRect(trackColor.copy(alpha = 0.7f)) }
                )
        )
        // Заполненная часть (убывает слева направо)
        Box(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth(progressCoerced)
                .padding(1.5.dp)
                .background(fillColor, RoundedCornerShape(4.dp))
        )
    }
}
