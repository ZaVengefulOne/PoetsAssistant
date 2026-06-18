package com.example.composejoyride.ui.screens.poem

import android.app.Activity
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composejoyride.ui.theme.composables.VengTopAppBar
import com.example.composejoyride.ui.viewModels.PoemViewModel

@Composable
fun PoemScreen(preferences: SharedPreferences) {
    val viewModel: PoemViewModel = hiltViewModel()
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadPoemOfDay()
    }

    Scaffold(
        topBar = {
            val title = when (val state = uiState) {
                is PoemUiState.Success -> state.title
                is PoemUiState.Error -> "Ошибка"
                PoemUiState.Loading -> "Стих дня"
            }

            VengTopAppBar(
                navigationAction = { (context as? Activity)?.finish() },
                title = title,
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                actions = {
                    if (uiState is PoemUiState.Success) {
                        val starred = (uiState as PoemUiState.Success).isStarred
                        IconButton(onClick = { viewModel.toggleStar() }) {
                            Icon(
                                imageVector = if (starred) {
                                    Icons.Filled.Star
                                } else {
                                    Icons.Outlined.StarBorder
                                },
                                contentDescription = if (starred) {
                                    "Убрать из избранного"
                                } else {
                                    "Добавить в избранное"
                                },
                            )
                        }
                    }
                },
            )
        },
        floatingActionButton = {
            if (uiState is PoemUiState.Success) {
                ExtendedFloatingActionButton(
                    onClick = { viewModel.regeneratePoemOfDay() },
                    icon = { Icon(Icons.Filled.Refresh, contentDescription = null) },
                    text = { Text("Другой стих") },
                )
            }
        },
    ) { padding ->
        when (val state = uiState) {
            PoemUiState.Loading -> {
                CircularProgressIndicator()
            }
            is PoemUiState.Error -> Text(state.message, Modifier.padding(padding).padding(16.dp))
            is PoemUiState.Success -> {
                Text(
                    text = state.text,
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 24.sp),
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
        }
    }
}