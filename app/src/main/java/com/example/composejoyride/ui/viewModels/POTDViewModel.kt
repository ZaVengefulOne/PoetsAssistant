package com.example.composejoyride.ui.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composejoyride.data.entitites.Poem
import com.example.composejoyride.data.repositories.interfaces.IPoemRepository
import com.example.composejoyride.data.utils.displayTitle
import com.example.composejoyride.data.widget.PoemWidget
import com.example.composejoyride.ui.screens.potd.POTDUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class POTDViewModel @Inject constructor(
    private val repository: IPoemRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow<POTDUiState>(POTDUiState.Loading)
    val uiState: StateFlow<POTDUiState> = _uiState

    fun loadPoemOfDay() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = POTDUiState.Loading
            runCatching { repository.getPoemOfDay() }
                .onSuccess { poem -> _uiState.value = poem.toUiState() }
                .onFailure { _uiState.value = POTDUiState.Error("Не удалось загрузить стих") }
        }
    }

    fun regeneratePoemOfDay() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = POTDUiState.Loading
            runCatching { repository.regeneratePoemOfDay() }
                .onSuccess { poem ->
                    _uiState.value = poem.toUiState()
                    poem?.let { PoemWidget.refreshAll(context, it) }
                }
                .onFailure {
                    _uiState.value = POTDUiState.Error("Не удалось загрузить стих")
                }
        }
    }

    fun toggleStar() {
        val current = _uiState.value as? POTDUiState.Success ?: return
        viewModelScope.launch(Dispatchers.IO) {
            repository.toggleStar(current.id)
            _uiState.value = current.copy(isStarred = !current.isStarred)
        }
    }

//    private suspend fun updateWidgets() {
//        val manager = GlanceAppWidgetManager(context)
//        manager.getGlanceIds(PoemWidget::class.java).forEach { glanceId ->
//            PoemWidget().update(context, glanceId)
//        }
//    }

    private fun Poem?.toUiState(): POTDUiState =
        if (this == null) {
            POTDUiState.Error("Не удалось загрузить стих")
        } else {
            POTDUiState.Success(
                title = displayTitle(),
                text = poemText,
                author = author ?: "Автор неизвестен",
                id = id,
                isStarred = isStarred,
            )
        }
}