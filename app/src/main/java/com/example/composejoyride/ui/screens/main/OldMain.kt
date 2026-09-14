package com.example.composejoyride.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.ContactPage
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.R
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.data.utils.VengButtonType
import com.example.composejoyride.ui.theme.Dimens
import com.example.composejoyride.ui.theme.TheFont
import com.example.composejoyride.ui.theme.composables.VengButton
import com.example.composejoyride.ui.viewModels.MainViewModel

@Composable
fun OldMain(
    navController: NavController,
    paddingMain: Dp,
    paddingText: Dp,
    spacerTop: Dp,
    buttonColor: ButtonColors,
    buttonText: Color) {
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
                onClick = { navController.navigate(NoteGraph.STARRED_POEMS_SCREEN) },
                modifier = Modifier.fillMaxWidth(),
                buttonColor = buttonColor,
                textColor = buttonText,
                image = Icons.Filled.Star,
                text = stringResource(id = R.string.starred_poems),
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

            Spacer(modifier = Modifier.height(Dimens.paddingSpacer))

            VengButton(
                onClick = { navController.navigate(NoteGraph.POEM_ANALYZER_SCREEN) },
                modifier = Modifier.fillMaxWidth(),
                buttonColor = buttonColor,
                textColor = buttonText,
                image = Icons.Filled.Analytics,
                text = stringResource(id = R.string.poem_analyze),
                buttonType = VengButtonType.Liquid
            )
        }
    }
}