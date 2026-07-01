package com.example.composejoyride.ui.screens.potd

import android.app.Activity
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composejoyride.ui.theme.composables.VengTopAppBar
import com.example.composejoyride.ui.viewModels.POTDViewModel

@Composable
fun POTDScreen() {
    val viewModel: POTDViewModel = hiltViewModel()
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadPoemOfDay()
    }

    Scaffold(
        topBar = {
            val title = when (val state = uiState) {
                is POTDUiState.Success -> state.title
                is POTDUiState.Error -> "Ошибка"
                POTDUiState.Loading -> "Стих дня"
            }

            VengTopAppBar(
                navigationAction = { (context as? Activity)?.finish() },
                title = title,
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                actions = {
                    if (uiState is POTDUiState.Success) {
                        val starred = (uiState as POTDUiState.Success).isStarred
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
            if (uiState is POTDUiState.Success) {
                ExtendedFloatingActionButton(
                    onClick = { viewModel.regeneratePoemOfDay() },
                    icon = { Icon(Icons.Filled.Refresh, contentDescription = null) },
                    text = { Text("Другой стих", color = MaterialTheme.colorScheme.tertiary) },
                )
            }
        },
    ) { padding ->
        when (val state = uiState) {
            POTDUiState.Loading -> {
                CircularProgressIndicator()
            }

            is POTDUiState.Error -> Text(
                state.message, Modifier
                    .padding(padding)
                    .padding(16.dp)
            )

            is POTDUiState.Success -> {
                Column(
                    modifier = Modifier
                        .verticalScroll
                            (
                            state = rememberScrollState(),
                            enabled = true
                        )
                        .padding(bottom = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.text,
                        modifier = Modifier
                            .padding(padding)
                            .padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 24.sp),
                        color = MaterialTheme.colorScheme.tertiary,
                    )
                    Text(
                        text = state.author,
                        modifier = Modifier.padding(bottom = 24.dp, top = 8.dp),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            lineHeight = 24.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.tertiary,
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}