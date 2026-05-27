package com.example.composejoyride.ui.viewModels

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composejoyride.data.repositories.RhymeRepository
import com.example.composejoyride.data.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RhymeViewModel @Inject constructor(
    private val repository: RhymeRepository,
) : ViewModel() {

    private val _input = MutableStateFlow("")
    val input: StateFlow<String> = _input.asStateFlow()

    private val _result = MutableStateFlow<List<String>>(emptyList())
    val result: StateFlow<List<String>> = _result.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _hasError = MutableStateFlow(false)
    val hasError: StateFlow<Boolean> = _hasError.asStateFlow()

    fun setInput(newInput: String) {
        _input.value = newInput
    }

    fun vowelCount(word: String): Int =
        word.count { it in Constants.vowels }

    fun findRhymes(stressIndex: Int) {
        val word = _input.value.trim()
        if (word.isBlank()) return

        viewModelScope.launch {
            _isLoading.value = true
            _hasError.value = false
            _result.value = emptyList()
            try {
                _result.value = repository.getRhymes(word, stressIndex)
            } catch (e: Exception) {
                Log.e(TAG, "findRhymes failed for word=$word stress=$stressIndex", e)
                _hasError.value = true
                _result.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun copyToClipBoard(context: Context, rhymeItem: String) {
        val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData: ClipData = ClipData.newPlainText("text", rhymeItem)
        clipboardManager.setPrimaryClip(clipData)
    }

    private companion object {
        const val TAG = "RhymeViewModel"
    }
}
