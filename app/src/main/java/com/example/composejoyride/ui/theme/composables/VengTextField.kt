package com.example.composejoyride.ui.theme.composables

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.composejoyride.ui.theme.VengTextFieldShape
import com.example.composejoyride.ui.theme.vengOutlinedTextFieldColors
import com.example.composejoyride.ui.theme.vengRichTextEditorColors
import com.example.composejoyride.ui.theme.vengTextFieldTextStyle
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.ui.material3.OutlinedRichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorColors

@Composable
fun VengOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = vengTextFieldTextStyle(),
    shape: Shape = VengTextFieldShape,
    colors: TextFieldColors = vengOutlinedTextFieldColors(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        shape = shape,
        colors = colors,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VengRichTextEditor(
    state: RichTextState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = vengTextFieldTextStyle(fontSize = 18.sp),
    placeholder: @Composable (() -> Unit)? = null,
    shape: Shape = VengTextFieldShape,
    colors: RichTextEditorColors = vengRichTextEditorColors(),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    OutlinedRichTextEditor(
        state = state,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        placeholder = placeholder,
        shape = shape,
        colors = colors,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
    )
}

@Composable
fun VengPlaceholderText(
    text: String,
    fontSize: TextUnit = 18.sp,
) {
    Text(
        text = text,
        style = vengTextFieldTextStyle(
            fontSize = fontSize,
            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
        ),
    )
}
