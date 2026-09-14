package com.example.composejoyride.ui.screens.main

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.ContactPage
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.ToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composejoyride.R
import com.example.composejoyride.data.utils.Constants
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.data.utils.VengButtonType
import com.example.composejoyride.data.utils.sharedViewModel
import com.example.composejoyride.ui.theme.Dimens
import com.example.composejoyride.ui.theme.LocalTheme
import com.example.composejoyride.ui.theme.TheFont
import com.example.composejoyride.ui.theme.composables.VengButton
import com.example.composejoyride.ui.viewModels.MainViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MainScreen(navController: NavController, preferences: SharedPreferences) {
    val paddingMain = 40.dp
    val spacerTop = 48.dp
    val paddingText = 24.dp

    val buttonColor = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
    val buttonText = MaterialTheme.colorScheme.tertiary

    val viewModel = sharedViewModel<MainViewModel>(navController)
    val isNewMainUsed = viewModel.isNewMainUsed.collectAsState().value

    LaunchedEffect(Unit) {
        LocalTheme.value = preferences.getBoolean(Constants.EDIT_KEY, false)
        viewModel.ensurePoemsLoaded()
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, end = 8.dp)
        ) {
            Text(
                text = "Новая главная:",
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .align(Alignment.CenterVertically),
                color = MaterialTheme.colorScheme.tertiary,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 14.sp)
            )
            Switch(
                checked = isNewMainUsed,
                onCheckedChange = { viewModel.toggleNewMain() },
                modifier = Modifier,
            )
        }
        if (isNewMainUsed) {
            NewMain(
                navController = navController,
                paddingMain = paddingMain,
                paddingText = paddingText
            )
        } else {
            OldMain(
                navController = navController,
                paddingMain = paddingMain,
                paddingText = paddingText,
                spacerTop = spacerTop,
                buttonColor = buttonColor,
                buttonText = buttonText
            )
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    val navController = rememberNavController()
    val localContext = LocalContext.current
    val preferences = localContext.getSharedPreferences(Constants.PREFERENCES_MAIN, MODE_PRIVATE)
    MainScreen(navController, preferences)
}