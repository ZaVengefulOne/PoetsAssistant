package com.example.composejoyride.ui.screens

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.ContactPage
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

@Composable
fun MainScreen(navController: NavController, preferences: SharedPreferences) {
    val paddingMain = 40.dp
    val spacerTop = 48.dp
    val paddingText = 24.dp

    val buttonColor = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
    val buttonText = MaterialTheme.colorScheme.tertiary

    val viewModel = sharedViewModel<MainViewModel>(navController)

    LaunchedEffect(Unit) {
        LocalTheme.value = preferences.getBoolean(Constants.EDIT_KEY, false)
        viewModel.ensurePoemsLoaded()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = paddingMain),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier
                .padding(top = paddingText, bottom = paddingText)
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(
                fontFamily = TheFont,
                fontSize = 50.sp,
                lineHeight = 65.sp
            )
        )

        Spacer(modifier = Modifier.height(spacerTop))

        Column {

            VengButton(
                onClick = { navController.navigate(NoteGraph.AOTD_SCREEN) },
                modifier = Modifier.fillMaxWidth(),
                buttonColor = buttonColor,
                textColor = buttonText,
                image = Icons.Filled.ContactPage,
                text = stringResource(id = R.string.aotd),
                buttonType = VengButtonType.Liquid
            )

            Spacer(modifier = Modifier.height(Dimens.paddingSpacer))

            VengButton(
                onClick = { navController.navigate(NoteGraph.GENERATOR_SCREEN) },
                modifier = Modifier.fillMaxWidth(),
                buttonColor = buttonColor,
                textColor = buttonText,
                image = Icons.Filled.Abc,
                text = stringResource(id = R.string.generator),
                buttonType = VengButtonType.Liquid
            )

            Spacer(modifier = Modifier.height(Dimens.paddingSpacer))

            VengButton(
                onClick = { navController.navigate(NoteGraph.LIBRARY_SCREEN) },
                modifier = Modifier.fillMaxWidth(),
                buttonColor = buttonColor,
                textColor = buttonText,
                image = Icons.Filled.AutoStories,
                text = stringResource(id = R.string.library),
                buttonType = VengButtonType.Liquid
            )

            Spacer(modifier = Modifier.height(Dimens.paddingSpacer))

            VengButton(
                onClick = { navController.navigate(NoteGraph.SETTINGS_SCREEN) },
                modifier = Modifier.fillMaxWidth(),
                buttonColor = buttonColor,
                textColor = buttonText,
                icon = R.drawable.baseline_settings_24,
                text = stringResource(id = R.string.settings),
                buttonType = VengButtonType.Liquid
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