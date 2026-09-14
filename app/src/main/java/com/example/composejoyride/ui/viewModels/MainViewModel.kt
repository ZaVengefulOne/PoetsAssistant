package com.example.composejoyride.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composejoyride.data.repositories.interfaces.IPoemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: IPoemRepository,
) : ViewModel() {

    private val _isNewMainUsed = MutableStateFlow(false)
    val isNewMainUsed: StateFlow<Boolean> get() = _isNewMainUsed

    fun toggleNewMain() {
        _isNewMainUsed.value = !_isNewMainUsed.value
    }

    fun ensurePoemsLoaded() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { repository.ensurePoemsLoaded() }
                .onFailure {
                    Log.e("RIFMA", "Error: ensurePoemsLoaded have failed.")
                }
        }
    }
}