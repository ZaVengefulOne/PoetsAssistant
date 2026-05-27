package com.example.composejoyride.ui.theme.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.composejoyride.R
import com.example.composejoyride.ui.theme.TheFont

@Composable
fun RhymeStressInfoDialog(
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = stringResource(R.string.rhyme_stress_info_title),
                fontFamily = TheFont,
                color = MaterialTheme.colorScheme.tertiary,
            )
        },
        text = {
            Text(
                text = stringResource(R.string.rhyme_stress_info_message),
                fontFamily = TheFont,
                color = MaterialTheme.colorScheme.tertiary,
            )
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text(
                    text = stringResource(R.string.ok),
                    fontFamily = TheFont,
                    color = MaterialTheme.colorScheme.tertiary,
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
    )
}
