package com.example.composejoyride

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.composejoyride.data.utils.Constants
import com.example.composejoyride.ui.theme.ComposeJoyrideTheme
import com.example.composejoyride.ui.theme.composables.VengScaffold
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
        setContent {
            val sharedPrefs = getSharedPreferences(Constants.PREFERENCES_MAIN, MODE_PRIVATE)
            ComposeJoyrideTheme {
                val navController = rememberNavController()
                val isBottomBarVisible = remember { mutableStateOf(true) }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorScheme.background
                ) {
                    VengScaffold(
                        navController = navController,
                        bottomBarVisibility = isBottomBarVisible,
                        preferences = sharedPrefs,
                    )
                }
            }
        }
    }
}
