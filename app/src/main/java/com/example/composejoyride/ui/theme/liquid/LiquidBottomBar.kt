package com.example.composejoyride.ui.theme.liquid

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kyant.backdrop.Backdrop
import com.kyant.backdrop.backdrops.rememberCombinedBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy
import com.kyant.capsule.AbsoluteContinuousCapsule
import kotlinx.coroutines.launch

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun LiquidBottomBar(
    backdrop: Backdrop,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    tabCount: Int,
    content: @Composable (index: Int) -> Unit
) {
    val barBackdrop = rememberLayerBackdrop()
    val scope = rememberCoroutineScope()
    val animatedIndex = remember { Animatable(selectedIndex.toFloat()) }

    LaunchedEffect(selectedIndex) {
        scope.launch {
            animatedIndex.animateTo(
                targetValue = selectedIndex.toFloat(),
                animationSpec = tween(300)
            )
        }
    }

    Box(
        modifier
            .safeContentPadding()
            .fillMaxWidth()
            .height(72f.dp)
            .drawBackdrop(
                backdrop = backdrop,
                exportedBackdrop = barBackdrop,
                shape = { RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp) },
                effects = {
                    vibrancy()
                    blur(4f.dp.toPx())
                    lens(24f.dp.toPx(), 48f.dp.toPx())
                },
                onDrawSurface = { drawRect(Color.White.copy(alpha = 0.15f)) }
            ),
        contentAlignment = Alignment.Center
    ) {
        BoxWithConstraints(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            val gap = 8.dp
            val itemWidth = (maxWidth - gap * (tabCount - 1)) / tabCount
            val pillWidth = itemWidth * 0.85f
            val offsetX = (itemWidth + gap) * animatedIndex.value + (itemWidth - pillWidth) / 2

            Box(
                Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = offsetX)
                    .width(pillWidth)
                    .fillMaxHeight()
                    .drawBackdrop(
                        backdrop = rememberCombinedBackdrop(backdrop, barBackdrop),
                        shape = { AbsoluteContinuousCapsule },
                        effects = {
                            vibrancy()
                            blur(3f.dp.toPx())
                            lens(16f.dp.toPx(), 32f.dp.toPx(), chromaticAberration = true)
                        },
                        onDrawSurface = { drawRect(Color.White.copy(alpha = 0.15f)) }
                    )
            )

            Row(
                Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(gap),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(tabCount) { index ->
                    Box(
                        Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        LiquidButton(
                            onClick = { onTabSelected(index) },
                            backdrop = barBackdrop,
                            modifier = Modifier.fillMaxWidth(0.9f),
                            isInteractive = true
                        ) {
                            content(index)
                        }
                    }
                }
            }
        }
    }
}
