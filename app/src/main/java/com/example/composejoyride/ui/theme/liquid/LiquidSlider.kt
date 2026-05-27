package com.example.composejoyride.ui.theme.liquid

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.kyant.backdrop.Backdrop
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberCombinedBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.lens
import kotlin.math.roundToInt

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun GlassSlider(
    backdrop: Backdrop,
    modifier: Modifier = Modifier,
    value: Float = 0.5f,
    onValueChange: (Float) -> Unit = {},
    trackColor: Color = Color(0xFF2E7D32),
) {
    var sliderValue by remember { mutableFloatStateOf(value.coerceIn(0f, 1f)) }
    val density = LocalDensity.current
    val thumbWidth = 56.dp
    val thumbHeight = 32.dp

    BoxWithConstraints(
        modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        val trackBackdrop = rememberLayerBackdrop()
        val trackWidthPx = with(density) { maxWidth.toPx() }
        val thumbWidthPx = with(density) { thumbWidth.toPx() }
        val rangePx = (trackWidthPx - thumbWidthPx).coerceAtLeast(1f)

        fun positionToValue(px: Float): Float =
            ((px - thumbWidthPx / 2).coerceIn(0f, trackWidthPx - thumbWidthPx) / rangePx).coerceIn(0f, 1f)

        Box(
            Modifier
                .fillMaxWidth()
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragStart = { offset ->
                            val newValue = positionToValue(offset.x)
                            sliderValue = newValue
                            onValueChange(newValue)
                        },
                        onHorizontalDrag = { _, dragAmount ->
                            val newValue = (sliderValue + dragAmount / rangePx).coerceIn(0f, 1f)
                            sliderValue = newValue
                            onValueChange(newValue)
                        }
                    )
                }
        ) {
            Box(
                Modifier
                    .align(Alignment.Center)
                    .layerBackdrop(trackBackdrop)
                    .background(trackColor, CircleShape)
                    .height(6.dp)
                    .fillMaxWidth()
            )

            val thumbOffsetPx = sliderValue * rangePx
            Box(
                Modifier
                    .align(Alignment.CenterStart)
                    .offset { IntOffset(thumbOffsetPx.roundToInt(), 0) }
                    .drawBackdrop(
                        backdrop = rememberCombinedBackdrop(backdrop, trackBackdrop),
                        shape = { CircleShape },
                        effects = {
                            lens(
                                refractionHeight = 12f.dp.toPx(),
                                refractionAmount = 16f.dp.toPx(),
                                chromaticAberration = true
                            )
                        }
                    )
                    .size(thumbWidth, thumbHeight)
            )
        }
    }
}
