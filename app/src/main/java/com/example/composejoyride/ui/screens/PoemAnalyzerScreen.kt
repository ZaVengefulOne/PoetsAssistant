package com.example.composejoyride.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.data.utils.VengButtonType
import com.example.composejoyride.data.utils.formatPercents
import com.example.composejoyride.data.utils.formatPoem
import com.example.composejoyride.data.utils.sharedViewModel
import com.example.composejoyride.ui.theme.TheFont
import com.example.composejoyride.ui.theme.composables.VengButton
import com.example.composejoyride.ui.theme.composables.VengOutlinedTextField
import com.example.composejoyride.ui.theme.composables.VengTopAppBar
import com.example.composejoyride.ui.theme.liquid.LocalScrollBottomInset
import com.example.composejoyride.ui.theme.vengTextFieldTextStyle
import com.example.composejoyride.ui.viewModels.PoemAnalyzerViewModel

@Composable
fun PoemAnalyzerScreen(navController: NavController) {
    val viewmodel: PoemAnalyzerViewModel = sharedViewModel(navController)
    val sendPoemText = viewmodel.sendText.collectAsState().value
    val poemText = viewmodel.poemText.collectAsState().value
    val score = viewmodel.score.collectAsState().value
    val meter = viewmodel.meter.collectAsState().value
    val rhymeScheme = viewmodel.rhymeScheme.collectAsState().value
    val scrollBottomInset = LocalScrollBottomInset.current
    val textColor = MaterialTheme.colorScheme.tertiary
    val fontSize = 22.sp
    val spacerPadding = 12.dp
    val textPadding = 16.dp

    Scaffold(
        topBar = {
            VengTopAppBar(
                navigationAction = { navController.navigate(NoteGraph.MAIN_SCREEN) },
                title = "Анализ стиха",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack
            )
        }) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(bottom = scrollBottomInset + 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                VengOutlinedTextField(
                    value = sendPoemText,
                    onValueChange = { viewmodel.setSendText(it) },
                    label = { Text("Текст стиха", fontFamily = TheFont) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    textStyle = vengTextFieldTextStyle(fontSize = fontSize),
                    singleLine = false,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                    )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = spacerPadding),
                    horizontalArrangement = Arrangement.Center
                ) {
                    VengButton(
                        onClick = {
                            viewmodel.analyzePoem()
                            viewmodel.setSendText("")
                        },
                        text = "Анализировать!",
                        buttonType = VengButtonType.Liquid
                    )
                }
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = formatPoem(poemText),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        lineHeight = fontSize
                    ),
                    color = textColor,
                    modifier = Modifier
                        .padding(textPadding)
                )
                Spacer(modifier = Modifier.width(spacerPadding))
                Text(
                    text = "Процент совпадения: ${formatPercents(score)}",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        lineHeight = fontSize
                    ),
                    color = textColor,
                    modifier = Modifier
                        .padding(textPadding)
                )
                Spacer(modifier = Modifier.width(spacerPadding))
                Text(
                    text = "Размер слога стиха: $meter",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        lineHeight = fontSize
                    ),
                    color = textColor,
                    modifier = Modifier
                        .padding(textPadding)
                )
                Spacer(modifier = Modifier.width(spacerPadding))
                Text(
                    text = "Схема стиха: $rhymeScheme",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        lineHeight = fontSize
                    ),
                    color = textColor,
                    modifier = Modifier
                        .padding(textPadding)
                )
            }
        }
    }
}