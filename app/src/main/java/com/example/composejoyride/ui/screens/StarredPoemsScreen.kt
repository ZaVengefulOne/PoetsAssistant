package com.example.composejoyride.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.data.utils.displayTitle
import com.example.composejoyride.data.utils.sharedViewModel
import com.example.composejoyride.ui.theme.Dimens
import com.example.composejoyride.ui.theme.TheFont
import com.example.composejoyride.ui.theme.composables.VengListItem
import com.example.composejoyride.ui.viewModels.PoemViewModel
import com.example.composejoyride.ui.viewModels.StarredPoemsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Starred(
    navController: NavController,
    isBottomBarVisible: MutableState<Boolean>
) {
    val viewmodel = sharedViewModel<StarredPoemsViewModel>(navController)
    val poemViewModel = sharedViewModel<PoemViewModel>(navController)
    val starredPoems = viewmodel._poemList.collectAsState().value

    LaunchedEffect(Unit) {
        viewmodel.getStarredPoems()
        isBottomBarVisible.value = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarColors(
                    containerColor = colorScheme.secondary,
                    scrolledContainerColor = colorScheme.secondary,
                    navigationIconContentColor = colorScheme.primary,
                    titleContentColor = colorScheme.secondary,
                    actionIconContentColor = colorScheme.primary,
                    subtitleContentColor = colorScheme.secondary
                ),
                title = {
                    Text(
                        text = "Избранные стихи",
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = TheFont,
                        color = colorScheme.tertiary,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        isBottomBarVisible.value = true
                        navController.navigate(NoteGraph.MAIN_SCREEN)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }

                }
            )
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(
                    start = 4.dp,
                    end = 4.dp,
                ),
                content = {
                    items(starredPoems) { poemItem ->
                        VengListItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(72.dp),
                            onClick = {
                                poemViewModel.loadPoem(poemItem.id)
                                navController.navigate(NoteGraph.POEM_SCREEN)
                            },
                        ) {
                            Icon(
                                Icons.Filled.AutoStories,
                                contentDescription = "Article Icon",
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(start = Dimens.paddingMedium),
                                tint = colorScheme.primary,
                            )
                            Text(
                                modifier = Modifier.padding(start = Dimens.paddingMedium),
                                text = poemItem.title ?: poemItem.displayTitle(),
                                fontSize = 20.sp,
                                color = colorScheme.tertiary,
                                fontFamily = TheFont,
                                textAlign = TextAlign.Start,
                            )
                        }
                    }
                }
            )
        }
    }
}