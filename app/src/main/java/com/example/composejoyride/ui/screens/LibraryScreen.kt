package com.example.composejoyride.ui.screens

//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.composejoyride.R
import com.example.composejoyride.data.network.interactors.ParseInteractor.Companion.SEARCH_KEY
import com.example.composejoyride.data.utils.NoteGraph
import com.example.composejoyride.data.utils.sharedViewModel
import com.example.composejoyride.ui.theme.Dimens
import com.example.composejoyride.ui.theme.TheFont
import com.example.composejoyride.ui.theme.composables.VengListItem
import com.example.composejoyride.ui.theme.composables.VengOutlinedTextField
import com.example.composejoyride.ui.theme.composables.VengTopAppBar
import com.example.composejoyride.ui.theme.liquid.LocalScrollBottomInset
import com.example.composejoyride.ui.viewModels.ArticleViewModel
import com.example.composejoyride.ui.viewModels.LibraryViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun Library(
    navController: NavController,
    preferences: SharedPreferences,
    isBottomBarVisible: MutableState<Boolean>
) {

    val viewModel: LibraryViewModel = sharedViewModel(navController)
    val articleViewModel: ArticleViewModel = sharedViewModel(navController)

    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(imeVisible) {
        isBottomBarVisible.value = !imeVisible
    }

    LaunchedEffect(Unit) {
        viewModel.getArticles()
    }

    val colorScheme = MaterialTheme.colorScheme
    val scrollBottomInset = LocalScrollBottomInset.current
    val articles = viewModel.articleItems.collectAsState().value
    val isLoaded = viewModel.isLoaded.collectAsState().value

    val searchText = rememberSaveable { mutableStateOf("") }
    val filteredArticleList = rememberSaveable { mutableStateOf(articles) }
    val localItems = rememberSaveable {
        mutableStateOf(
            preferences.getStringSet(SEARCH_KEY, mutableSetOf())?.toMutableSet()
                ?: mutableSetOf()
        )
    }
    val expanded = rememberSaveable { mutableStateOf(false) }
    val trailingIconView = @Composable {
        if (searchText.value.isNotEmpty()) {
            IconButton(onClick = {
                searchText.value = ""
                filteredArticleList.value = articles
            }) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "Close Button",
                    modifier = Modifier.size(25.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        } else {
            IconButton(
                onClick = { expanded.value = !expanded.value }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_keyboard_arrow_down_24),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
    Scaffold(
        topBar = {
            VengTopAppBar(
                navigationAction = {navController.navigate(NoteGraph.MAIN_SCREEN)},
                title = "Библиотека статей",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!isLoaded) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
                }
            } else {
                Row {
                    VengOutlinedTextField(
                        value = searchText.value,
                        onValueChange = {
                            searchText.value = it
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .onFocusChanged { isBottomBarVisible.value = !it.isFocused },
                        placeholder = {
                            Text(
                                text = "Поиск...",
                                modifier = Modifier.clickable { expanded.value = true },
                                color = colorScheme.onSurface.copy(alpha = 0.55f),
                                fontFamily = TheFont,
                            )
                        },
                        singleLine = true,
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (searchText.value.isEmpty()) {
                                    filteredArticleList.value = articles
                                } else {
                                    filteredArticleList.value = articles.filter {
                                        it.articleTitle.contains(searchText.value, true)
                                    }
                                    viewModel.saveSearchHistory(searchText.value, preferences)
                                    localItems.value =
                                        preferences.getStringSet(
                                            SEARCH_KEY,
                                            mutableSetOf()
                                        )
                                            ?.toMutableSet()
                                            ?: mutableSetOf()
                                }
                                isBottomBarVisible.value = true
                                keyboardController?.hide()
                            }
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        trailingIcon = trailingIconView
                    )
                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false },
                        containerColor = MaterialTheme.colorScheme.secondary,
                        shadowElevation = 5.dp
                    ) {
                        localItems.value.forEach { item ->
                            DropdownMenuItem(text = {
                                Text(text = item)
                            }, onClick = {
                                searchText.value = item
                                expanded.value = false
                            })
                        }
                    }
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(
                        start = 4.dp,
                        end = 4.dp,
                        bottom = scrollBottomInset + 8.dp,
                    ),
                    content = {
                        items(filteredArticleList.value.ifEmpty { articles }) { articleItem ->
                            VengListItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(72.dp),
                                onClick = {
                                    articleViewModel.getArticle(articleItem.articleLink)
                                    navController.navigate(NoteGraph.ARTICLE_SCREEN)
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
                                    text = articleItem.articleTitle,
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
}



