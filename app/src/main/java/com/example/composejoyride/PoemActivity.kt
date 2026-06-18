package com.example.composejoyride

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.composejoyride.data.utils.Constants
import com.example.composejoyride.ui.screens.poem.PoemScreen
import com.example.composejoyride.ui.theme.ComposeJoyrideTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PoemActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val sharedPrefs = getSharedPreferences(Constants.PREFERENCES_POEM, MODE_PRIVATE)
            ComposeJoyrideTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorScheme.background
                ) {
                    PoemScreen(sharedPrefs)
                }
            }
        }
    }
}