package com.example.composejoyride.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.composejoyride.data.network.IServerInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PoemAnalyzerViewModel @Inject constructor(private val interactor: IServerInteractor) :
    ViewModel() {
    private val _poemText = MutableStateFlow("")
    val poemText: StateFlow<String> = _poemText

    private val _sendText = MutableStateFlow("")
    val sendText: StateFlow<String> = _sendText

    private val _score = MutableStateFlow(0.0)
    val score: StateFlow<Double> = _score

    private val _meter = MutableStateFlow("")
    val meter: StateFlow<String> = _meter

    private val _rhymeScheme = MutableStateFlow("")
    val rhymeScheme: StateFlow<String> = _rhymeScheme

    fun setPoemText(text: String){
        _poemText.value = text
    }

    fun setSendText(text: String){
        _sendText.value = text
    }

    fun analyzePoem() {
        viewModelScope.launch {
            val result = interactor.analyzePoem(sendText.value.splitToSequence("\n").toList())
            _poemText.value = result.stressedLines.toString()
            _score.value = result.score?: 0.0
            _meter.value = result.meter?: ""
            _rhymeScheme.value = result.rhymeScheme?: ""
        }
    }
}