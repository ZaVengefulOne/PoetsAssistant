package com.example.composejoyride.ui.screens.potd

sealed interface POTDUiState {
    data object Loading : POTDUiState
    data class Success(
        val title: String,
        val text: String,
        val author: String,
        val id: Int,
        val isStarred: Boolean,
    ) : POTDUiState
    data class Error(val message: String) : POTDUiState
}