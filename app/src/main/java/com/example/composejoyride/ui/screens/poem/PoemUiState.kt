package com.example.composejoyride.ui.screens.poem

sealed interface PoemUiState {
    data object Loading : PoemUiState
    data class Success(
        val title: String,
        val text: String,
        val author: String,
        val id: Int,
        val isStarred: Boolean,
    ) : PoemUiState
    data class Error(val message: String) : PoemUiState
}