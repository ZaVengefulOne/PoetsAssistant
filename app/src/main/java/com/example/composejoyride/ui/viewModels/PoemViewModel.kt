package com.example.composejoyride.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composejoyride.data.entitites.Poem
import com.example.composejoyride.data.repositories.interfaces.IPoemRepository
import com.example.composejoyride.data.utils.displayTitle
import com.example.composejoyride.ui.screens.poem.PoemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PoemViewModel @Inject constructor(private val repository: IPoemRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<PoemUiState>(PoemUiState.Loading)
    val uiState: StateFlow<PoemUiState> = _uiState

    fun loadPoem(poemId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = PoemUiState.Loading
            runCatching { repository.getPoem(poemId) }
                .onSuccess { poem -> _uiState.value = poem.toUiState() }
                .onFailure { _uiState.value = PoemUiState.Error("Не удалось загрузить стих") }
        }
    }

    fun toggleStar() {
        val current = _uiState.value as? PoemUiState.Success ?: return
        viewModelScope.launch(Dispatchers.IO) {
            repository.toggleStar(current.id)
            _uiState.value = current.copy(isStarred = !current.isStarred)
        }
    }

    private fun Poem?.toUiState(): PoemUiState =
        if (this == null) {
            PoemUiState.Error("Не удалось загрузить стих")
        } else {
            PoemUiState.Success(
                title = displayTitle(),
                text = poemText,
                author = author ?: "Автор неизвестен",
                id = id,
                isStarred = isStarred,
            )
        }
}