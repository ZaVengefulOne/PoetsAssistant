package com.example.composejoyride.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.R
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.data.utils.sharedViewModel
import com.example.composejoyride.ui.theme.Dimens
import com.example.composejoyride.ui.theme.TheFont
import com.example.composejoyride.ui.theme.composables.RhymeStressInfoDialog
import com.example.composejoyride.ui.theme.composables.VengOutlinedTextField
import com.example.composejoyride.ui.theme.composables.VengPlaceholderText
import com.example.composejoyride.ui.theme.composables.VengTopAppBar
import com.example.composejoyride.ui.theme.composables.VowelSelectionDialog
import com.example.composejoyride.ui.viewModels.RhymeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RhymeScreen(navController: NavController, isBottomBarVisible: MutableState<Boolean>) {
    val context = LocalContext.current
    val viewModel: RhymeViewModel = sharedViewModel(navController)
    val message by viewModel.input.collectAsState()
    val resultArray by viewModel.result.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val hasError by viewModel.hasError.collectAsState()
    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val colorScheme = MaterialTheme.colorScheme

    var showVowelDialog by remember { mutableStateOf(false) }
    var showStressInfoDialog by remember { mutableStateOf(false) }

    LaunchedEffect(imeVisible) {
        isBottomBarVisible.value = !imeVisible
    }

    if (showStressInfoDialog) {
        RhymeStressInfoDialog(onDismissRequest = { showStressInfoDialog = false })
    }

    if (showVowelDialog) {
        VowelSelectionDialog(
            word = message,
            onDismissRequest = { showVowelDialog = false },
            onStressIndexSelected = { stressIndex ->
                viewModel.findRhymes(stressIndex)
            },
        )
    }

    Scaffold(
        topBar = {
            VengTopAppBar(
                navigationAction = { navController.navigate(NoteGraph.MAIN_SCREEN) },
                title = "Генератор Рифм",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                actions = {
                    IconButton(onClick = { showStressInfoDialog = true }) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = stringResource(R.string.rhyme_stress_info_content_description),
                            tint = colorScheme.primary,
                        )
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            VengOutlinedTextField(
                modifier = Modifier
                    .padding(16.dp)
                    .onFocusChanged { isBottomBarVisible.value = !it.isFocused },
                value = message,
                onValueChange = viewModel::setInput,
                placeholder = { VengPlaceholderText(stringResource(R.string.enter_rhyming_word)) },
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (message.isNotBlank()) {
                            isBottomBarVisible.value = true
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            val vowels = viewModel.vowelCount(message)
                            if (vowels <= 1) {
                                viewModel.findRhymes(0)
                            } else {
                                showVowelDialog = true
                            }
                        }
                    },
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),
                trailingIcon = {
                    Icon(
                        Icons.Filled.Clear,
                        contentDescription = "Clear",
                        modifier = Modifier.clickable {
                            viewModel.setInput("")
                            isBottomBarVisible.value = true
                            keyboardController?.hide()
                        },
                        tint = colorScheme.primary,
                    )
                },
            )

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(Dimens.paddingMedium),
                    color = colorScheme.tertiary,
                )
            }

            if (hasError) {
                Text(
                    text = stringResource(R.string.rhyme_load_error),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.paddingMedium),
                    color = MaterialTheme.colorScheme.error,
                    fontFamily = TheFont,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                )
            }

            Text(
                text = "Найдено слов: ${resultArray.size}",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.paddingMedium)
                    .align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.tertiary,
                fontFamily = TheFont,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                content = {
                    items(resultArray) { rhymeItem ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                        ) {
                            Row {
                                Text(
                                    text = rhymeItem,
                                    color = MaterialTheme.colorScheme.tertiary,
                                    fontFamily = TheFont,
                                    fontSize = 28.sp,
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .weight(0.9f)
                                        .padding(start = 10.dp),
                                )
                                IconButton(
                                    onClick = { viewModel.copyToClipBoard(context, rhymeItem) },
                                    modifier = Modifier.weight(0.1f),
                                ) {
                                    Icon(
                                        Icons.Filled.ContentCopy,
                                        contentDescription = "Copy",
                                    )
                                }
                            }
                        }
                    }
                },
            )
        }
    }
}