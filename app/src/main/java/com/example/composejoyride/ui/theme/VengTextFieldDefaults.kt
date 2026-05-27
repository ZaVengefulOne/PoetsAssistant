package com.example.composejoyride.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorColors
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults

val VengTextFieldShape: Shape = RoundedCornerShape(16.dp)

@Composable
private fun vengTextSelectionColors(
    colorScheme: ColorScheme = MaterialTheme.colorScheme,
): TextSelectionColors = TextSelectionColors(
    handleColor = colorScheme.tertiary,
    backgroundColor = colorScheme.tertiary.copy(alpha = 0.35f),
)

/**
 * Colors for [com.mohamedrejeb.richeditor.ui.material3.OutlinedRichTextEditor].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun vengRichTextEditorColors(
    colorScheme: ColorScheme = MaterialTheme.colorScheme,
): RichTextEditorColors {
    val selectionColors = vengTextSelectionColors(colorScheme)
    return RichTextEditorDefaults.outlinedRichTextEditorColors(
        textColor = colorScheme.tertiary,
        disabledTextColor = colorScheme.tertiary.copy(alpha = 0.5f),
        containerColor = colorScheme.surfaceVariant,
        cursorColor = colorScheme.tertiary,
        errorCursorColor = colorScheme.error,
        selectionColors = selectionColors,
        focusedBorderColor = colorScheme.tertiary,
        unfocusedBorderColor = colorScheme.onSurface.copy(alpha = 0.45f),
        disabledBorderColor = colorScheme.onSurface.copy(alpha = 0.3f),
        errorBorderColor = colorScheme.error,
        focusedLeadingIconColor = colorScheme.primary,
        unfocusedLeadingIconColor = colorScheme.primary.copy(alpha = 0.7f),
        disabledLeadingIconColor = colorScheme.primary.copy(alpha = 0.4f),
        errorLeadingIconColor = colorScheme.error,
        focusedTrailingIconColor = colorScheme.primary,
        unfocusedTrailingIconColor = colorScheme.primary.copy(alpha = 0.7f),
        disabledTrailingIconColor = colorScheme.onSurface.copy(alpha = 0.4f),
        errorTrailingIconColor = colorScheme.error,
        focusedLabelColor = colorScheme.tertiary,
        unfocusedLabelColor = colorScheme.onSurface.copy(alpha = 0.75f),
        disabledLabelColor = colorScheme.onSurface.copy(alpha = 0.5f),
        errorLabelColor = colorScheme.error,
        placeholderColor = colorScheme.onSurface.copy(alpha = 0.55f),
        disabledPlaceholderColor = colorScheme.onSurface.copy(alpha = 0.4f),
        focusedSupportingTextColor = colorScheme.tertiary,
        unfocusedSupportingTextColor = colorScheme.onSurface.copy(alpha = 0.7f),
        disabledSupportingTextColor = colorScheme.onSurface.copy(alpha = 0.5f),
        errorSupportingTextColor = colorScheme.error,
    )
}

/**
 * Colors for Material3 [androidx.compose.material3.OutlinedTextField] — same palette as rich editor.
 */
@Composable
fun vengOutlinedTextFieldColors(
    colorScheme: ColorScheme = MaterialTheme.colorScheme,
): TextFieldColors {
    val selectionColors = vengTextSelectionColors(colorScheme)
    return TextFieldDefaults.colors(
        focusedTextColor = colorScheme.tertiary,
        unfocusedTextColor = colorScheme.tertiary,
        disabledTextColor = colorScheme.tertiary.copy(alpha = 0.5f),
        errorTextColor = colorScheme.error,
        cursorColor = colorScheme.tertiary,
        errorCursorColor = colorScheme.error,
        selectionColors = selectionColors,
        focusedContainerColor = colorScheme.surfaceVariant,
        unfocusedContainerColor = colorScheme.surfaceVariant.copy(alpha = 0.88f),
        disabledContainerColor = colorScheme.surfaceVariant.copy(alpha = 0.5f),
        errorContainerColor = colorScheme.surfaceVariant,
        focusedIndicatorColor = colorScheme.tertiary,
        unfocusedIndicatorColor = colorScheme.onSurface.copy(alpha = 0.45f),
        disabledIndicatorColor = colorScheme.onSurface.copy(alpha = 0.3f),
        errorIndicatorColor = colorScheme.error,
        focusedLabelColor = colorScheme.tertiary,
        unfocusedLabelColor = colorScheme.onSurface.copy(alpha = 0.75f),
        disabledLabelColor = colorScheme.onSurface.copy(alpha = 0.5f),
        errorLabelColor = colorScheme.error,
        focusedPlaceholderColor = colorScheme.onSurface.copy(alpha = 0.6f),
        unfocusedPlaceholderColor = colorScheme.onSurface.copy(alpha = 0.55f),
        disabledPlaceholderColor = colorScheme.onSurface.copy(alpha = 0.4f),
        errorPlaceholderColor = colorScheme.error,
        focusedTrailingIconColor = colorScheme.primary,
        unfocusedTrailingIconColor = colorScheme.primary.copy(alpha = 0.7f),
        disabledTrailingIconColor = colorScheme.primary.copy(alpha = 0.4f),
        errorTrailingIconColor = colorScheme.error,
        focusedLeadingIconColor = colorScheme.primary,
        unfocusedLeadingIconColor = colorScheme.primary.copy(alpha = 0.7f),
        disabledLeadingIconColor = colorScheme.primary.copy(alpha = 0.4f),
        errorLeadingIconColor = colorScheme.error,
    )
}

@Composable
fun vengTextFieldTextStyle(
    fontSize: androidx.compose.ui.unit.TextUnit = 18.sp,
    color: Color = MaterialTheme.colorScheme.tertiary,
): TextStyle = TextStyle(
    fontFamily = TheFont,
    fontSize = fontSize,
    color = color,
)

@Composable
fun VengTextFieldTheme(content: @Composable () -> Unit) {
    val selectionColors = vengTextSelectionColors()
    CompositionLocalProvider(
        androidx.compose.foundation.text.selection.LocalTextSelectionColors provides selectionColors,
    ) {
        content()
    }
}
