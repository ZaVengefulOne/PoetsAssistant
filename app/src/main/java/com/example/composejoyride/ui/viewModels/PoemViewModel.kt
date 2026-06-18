package com.example.composejoyride.ui.viewModels

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composejoyride.data.entitites.Poem
import com.example.composejoyride.data.repositories.interfaces.IPoemRepository
import com.example.composejoyride.data.utils.displayTitle
import com.example.composejoyride.data.widget.PoemWidget
import com.example.composejoyride.ui.screens.poem.PoemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PoemViewModel @Inject constructor(
    private val repository: IPoemRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow<PoemUiState>(PoemUiState.Loading)
    val uiState: StateFlow<PoemUiState> = _uiState

    fun loadPoemOfDay() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = PoemUiState.Loading
            runCatching { repository.getPoemOfDay() }
                .onSuccess { poem -> _uiState.value = poem.toUiState() }
                .onFailure { _uiState.value = PoemUiState.Error("Не удалось загрузить стих") }
        }
    }

    fun regeneratePoemOfDay() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = PoemUiState.Loading
            runCatching { repository.regeneratePoemOfDay() }
                .onSuccess { poem ->
                    _uiState.value = poem.toUiState()
                    poem?.let { PoemWidget.refreshAll(context, it) }
                }
                .onFailure {
                    _uiState.value = PoemUiState.Error("Не удалось загрузить стих")
                }
        }
    }

    fun toggleStar() {
        val current = _uiState.value as? PoemUiState.Success ?: return
        viewModelScope.launch(Dispatchers.IO) {
            repository.toggleStar(current.id)
            _uiState.value = current.copy(isStarred = !current.isStarred)
        }
    }

    private suspend fun updateWidgets() {
        val manager = GlanceAppWidgetManager(context)
        manager.getGlanceIds(PoemWidget::class.java).forEach { glanceId ->
            PoemWidget().update(context, glanceId)
        }
    }

    private fun Poem?.toUiState(): PoemUiState =
        if (this == null) {
            PoemUiState.Error("Не удалось загрузить стих")
        } else {
            PoemUiState.Success(
                title = displayTitle(),
                text = poemText,
                id = id,
                isStarred = isStarred,
            )
        }
}