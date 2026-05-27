package com.example.composejoyride.ui.theme.liquid

import android.os.Build

object LiquidGlassSupport {
  val enabled: Boolean
    get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
}
