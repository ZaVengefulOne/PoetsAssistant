package com.example.composejoyride.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.ArrowCircleUp
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.R
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.ui.theme.composables.VengIconButton


@Composable
fun NewMain(navController: NavController, paddingMain: Dp, paddingText: Dp) {

    val universalPadding = 8.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = paddingMain),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            VengIconButton(
                onClick = { navController.navigate(NoteGraph.AOTD_SCREEN) },
                imageVector = Icons.Filled.ArrowCircleUp,
                contentDescription = null,
                modifier = Modifier,
//                size = TODO(),
                useLiquid = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            VengIconButton(
                onClick = { navController.navigate(NoteGraph.GENERATOR_SCREEN) },
                imageVector = Icons.Filled.Abc,
                contentDescription = null,
                modifier = Modifier.padding(end = universalPadding),
//                size = TODO(),
                useLiquid = true,
            )
            Text(
                text = stringResource(id = R.string.app_name),
                modifier = Modifier
                    .padding(top = paddingText, bottom = paddingText)
                    .align(Alignment.CenterVertically),
                color = MaterialTheme.colorScheme.tertiary,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp)
            )
            VengIconButton(
                onClick = { navController.navigate(NoteGraph.LIBRARY_SCREEN) },
                imageVector = Icons.Filled.AutoStories,
                contentDescription = null,
                modifier = Modifier.padding(start = universalPadding),
//                size = TODO(),
                useLiquid = true,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            VengIconButton(
                onClick = { navController.navigate(NoteGraph.STARRED_POEMS_SCREEN) },
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                modifier = Modifier.padding(end = universalPadding),
//                size = TODO(),
                useLiquid = true,
            )
            VengIconButton(
                onClick = { navController.navigate(NoteGraph.SETTINGS_SCREEN) },
                imageVector = Icons.Filled.Settings,
                contentDescription = null,
                modifier = Modifier.padding(start = universalPadding),
//                size = TODO(),
                useLiquid = true,
            )
        }
    }
}