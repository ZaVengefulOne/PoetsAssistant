@file:Suppress("unused")

package com.example.composejoyride.ui.theme.composables.unused

import androidx.compose.runtime.Composable
import com.example.composejoyride.ui.theme.composables.VowelSelectionDialog as ActiveVowelSelectionDialog

@Deprecated("Use com.example.composejoyride.ui.theme.composables.VowelSelectionDialog")
@Composable
fun VowelSelectionDialog(
    word: String,
    onDismissRequest: () -> Unit,
    onVowelSelected: (vowelIndex: Int) -> Unit,
) {
    ActiveVowelSelectionDialog(
        word = word,
        onDismissRequest = onDismissRequest,
        onStressIndexSelected = onVowelSelected,
    )
}
