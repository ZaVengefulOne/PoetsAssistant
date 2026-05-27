package com.example.composejoyride.ui.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.R
import com.example.composejoyride.data.utils.sharedViewModel
import com.example.composejoyride.ui.theme.Dimens
import com.example.composejoyride.ui.theme.vengTextFieldTextStyle
import com.example.composejoyride.ui.theme.composables.RichTextFormattingToolbar
import com.example.composejoyride.ui.theme.composables.VengIconButton
import com.example.composejoyride.ui.theme.composables.VengOutlinedTextField
import com.example.composejoyride.ui.theme.composables.VengPlaceholderText
import com.example.composejoyride.ui.theme.composables.VengRichTextEditor
import com.example.composejoyride.ui.theme.liquid.LocalScrollBottomInset
import com.example.composejoyride.ui.viewModels.NoteViewModel
import com.mohamedrejeb.richeditor.model.rememberRichTextState

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun Note(
    navController: NavController,
    onDone: () -> Unit,
    isBottomBarVisible: MutableState<Boolean>,
) {
    val noteViewModel: NoteViewModel = sharedViewModel(navController)
    val note by noteViewModel.note.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollBottomInset = LocalScrollBottomInset.current
    val colorScheme = MaterialTheme.colorScheme

    val richState = rememberRichTextState()
    LaunchedEffect(Unit) {
        richState.setHtml(note.note_content_html)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = scrollBottomInset + 12.dp,
            ),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
        ) {
            VengIconButton(
                onClick = {
                    noteViewModel.updateNoteText(richState.toHtml())
                    noteViewModel.updateNote()
                    isBottomBarVisible.value = true
                    onDone()
                },
                imageVector = Icons.Filled.Save,
                contentDescription = "Сохранить",
                size = 56.dp,
            )
            VengIconButton(
                onClick = {
                    noteViewModel.deleteNote()
                    isBottomBarVisible.value = true
                    onDone()
                },
                imageVector = Icons.Filled.Delete,
                contentDescription = "Удалить",
                size = 56.dp,
                contentColor = colorScheme.error,
            )
        }

        VengOutlinedTextField(
            value = note.note_name,
            onValueChange = { noteViewModel.updateNoteName(it) },
            label = { Text(stringResource(R.string.newNote)) },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { if (it.isFocused) isBottomBarVisible.value = false },
            textStyle = vengTextFieldTextStyle(fontSize = 22.sp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    isBottomBarVisible.value = true
                    keyboardController?.hide()
                },
            ),
        )

        RichTextFormattingToolbar(richState)
        VengRichTextEditor(
            state = richState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = Dimens.paddingMedium)
                .onFocusChanged {
                    if (it.isFocused) isBottomBarVisible.value = false
                },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Default,
            ),
            placeholder = {
                VengPlaceholderText(stringResource(R.string.enter_note_text))
            },
        )

        BackHandler {
            isBottomBarVisible.value = true
            noteViewModel.updateNote()
        }
    }
}
