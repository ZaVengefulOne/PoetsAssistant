package com.example.composejoyride.ui.theme.composables

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.ui.theme.TheFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VengTopAppBar(navigationAction: () -> Unit, title: String, navigationIcon: ImageVector, style: TextStyle = MaterialTheme.typography.titleLarge,) {

    TopAppBar(
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            scrolledContainerColor = MaterialTheme.colorScheme.secondary,
            navigationIconContentColor = MaterialTheme.colorScheme.tertiary,
            titleContentColor = MaterialTheme.colorScheme.secondary,
            actionIconContentColor = MaterialTheme.colorScheme.secondary,
            subtitleContentColor = MaterialTheme.colorScheme.secondary
        ),
        title = {
            Text(
                fontSize = 24.sp,
                text = title,
                style = style,
                fontFamily = TheFont,
                color = MaterialTheme.colorScheme.tertiary,
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(onClick = navigationAction) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = "Navigation Icon"
                )
            }

        }
    )
}