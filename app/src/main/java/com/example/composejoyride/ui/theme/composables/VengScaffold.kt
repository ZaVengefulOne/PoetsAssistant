package com.example.composejoyride.ui.theme.composables

import android.content.SharedPreferences
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composejoyride.ui.theme.liquid.LiquidBottomBarHeight
import com.example.composejoyride.ui.theme.liquid.LiquidGlassSupport
import com.example.composejoyride.ui.theme.liquid.LocalLiquidBackdrop
import com.example.composejoyride.ui.theme.liquid.LocalScrollBottomInset
import com.kyant.backdrop.backdrops.layerBackdrop
import com.kyant.backdrop.backdrops.rememberLayerBackdrop

@Composable
fun VengScaffold(
    navController: NavHostController,
    bottomBarVisibility: MutableState<Boolean>,
    preferences: SharedPreferences,
    modifier: Modifier = Modifier,
) {
    val bottomBarVisible by bottomBarVisibility
    val navigationBarBottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val scrollBottomInset = if (bottomBarVisible) LiquidBottomBarHeight else 0.dp

    val navHostBottomPadding = if (LiquidGlassSupport.enabled) {
        navigationBarBottom
    } else {
        navigationBarBottom + scrollBottomInset
    }

    if (LiquidGlassSupport.enabled) {
        val rootBackdrop = rememberLayerBackdrop()

        CompositionLocalProvider(LocalScrollBottomInset provides scrollBottomInset) {
            Box(modifier.fillMaxSize()) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .layerBackdrop(rootBackdrop)
                ) {
                    NavHostContainer(
                        navController = navController,
                        padding = PaddingValues(bottom = navHostBottomPadding),
                        preferences = preferences,
                        bottomBarVisibility = bottomBarVisibility,
                    )
                }
                CompositionLocalProvider(LocalLiquidBackdrop provides rootBackdrop) {
                    Box(
                        Modifier
                            .align(Alignment.BottomCenter)
                            .navigationBarsPadding()
                    ) {
                        BottomNavigationBar(
                            navController = navController,
                            visibility = bottomBarVisibility,
                            backdrop = rootBackdrop,
                        )
                    }
                }
            }
        }
    } else {
        CompositionLocalProvider(LocalScrollBottomInset provides scrollBottomInset) {
            Box(modifier.fillMaxSize()) {
                NavHostContainer(
                    navController = navController,
                    padding = PaddingValues(bottom = navHostBottomPadding),
                    preferences = preferences,
                    bottomBarVisibility = bottomBarVisibility,
                )
                Box(
                    Modifier
                        .align(Alignment.BottomCenter)
                        .navigationBarsPadding()
                ) {
                    BottomNavigationBar(
                        navController = navController,
                        visibility = bottomBarVisibility,
                        backdrop = null,
                        useLiquid = false,
                    )
                }
            }
        }
    }
}
