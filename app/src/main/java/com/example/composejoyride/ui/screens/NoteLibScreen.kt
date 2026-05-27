package com.example.composejoyride.ui.screens

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.R
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.data.utils.sharedViewModel
import com.example.composejoyride.ui.theme.TheFont
import com.example.composejoyride.ui.theme.composables.AlertDialog
import com.example.composejoyride.ui.theme.composables.VengCard
import com.example.composejoyride.ui.theme.composables.VengIconButton
import com.example.composejoyride.ui.theme.composables.VengTopAppBar
import com.example.composejoyride.ui.theme.liquid.LocalScrollBottomInset
import com.example.composejoyride.ui.viewModels.NoteViewModel
import com.example.composejoyride.ui.viewModels.NotesViewModel

private val NoteCardWidth = 152.dp
private val NoteCardHeight = 128.dp
private val NoteGridCellSize = 164.dp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation", "UnrememberedGetBackStackEntry")
@Composable
fun Notes(navController: NavController) {
    val notesViewModel: NotesViewModel = sharedViewModel(navController)
    val noteViewModel: NoteViewModel = sharedViewModel(navController)

    val allNotes by notesViewModel.allNotes.collectAsState()
    val scrollBottomInset = LocalScrollBottomInset.current
    val colorScheme = MaterialTheme.colorScheme

    val openDeleteDialog = remember { mutableStateOf(false) }

    when {
        openDeleteDialog.value -> {
            AlertDialog(
                onDismissRequest = { openDeleteDialog.value = false },
                onConfirmation = {
                    openDeleteDialog.value = false
                    notesViewModel.clearNotes()
                },
                dialogTitle = stringResource(R.string.noteDeletion),
                dialogText = stringResource(R.string.noteDeletionRUSure),
                icon = Icons.Default.DeleteForever,
            )
        }
    }
    Scaffold(
        topBar = {
            VengTopAppBar(
                navigationAction = { navController.navigate(NoteGraph.MAIN_SCREEN) },
                title = "Заметки",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.FixedSize(NoteGridCellSize),
//                horizontalItemSpacing = 10.dp,
                verticalItemSpacing = 10.dp,
                contentPadding = PaddingValues(
                    start = 12.dp,
                    top = 16.dp,
                    end = 12.dp,
                    bottom = 12.dp,
                ),
                modifier = Modifier.weight(1f),
            ) {
                items(allNotes, key = { it.id }) { note ->
                    VengCard(
                        modifier = Modifier
                            .padding(2.dp)
                            .size(width = NoteCardWidth, height = NoteCardHeight),
                        onClick = {
                            noteViewModel.setNote(note.id)
                            navController.navigate(NoteGraph.NOTE_SCREEN)
                        },
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = 14.dp,
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Notes,
                                contentDescription = null,
                                tint = colorScheme.primary,
                                modifier = Modifier.size(36.dp),
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = note.note_name,
                                fontSize = 16.sp,
                                lineHeight = 20.sp,
                                fontFamily = TheFont,
                                color = colorScheme.tertiary,
                                textAlign = TextAlign.Center,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 8.dp,
                        bottom = scrollBottomInset + 12.dp,
                    ),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                VengIconButton(
                    onClick = {
                        noteViewModel.createAndOpenNewNote()
                        navController.navigate(NoteGraph.NOTE_SCREEN)
                    },
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Note",
                    size = 68.dp,
                )
                VengIconButton(
                    onClick = { openDeleteDialog.value = true },
                    imageVector = Icons.Default.DeleteForever,
                    contentDescription = "Clear Notes",
                    size = 68.dp,
                    contentColor = colorScheme.error,
                )
            }
        }
    }
}
