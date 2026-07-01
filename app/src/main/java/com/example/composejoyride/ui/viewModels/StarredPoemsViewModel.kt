package com.example.composejoyride.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composejoyride.data.entitites.Poem
import com.example.composejoyride.data.network.interactors.interfaces.IPoemInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StarredPoemsViewModel @Inject constructor(private val interactor: IPoemInteractor) :
    ViewModel() {
        private val poemList = MutableStateFlow<List<Poem>>(emptyList())
        val _poemList: StateFlow<List<Poem>>
            get() = poemList

    fun getStarredPoems() {
        viewModelScope.launch(Dispatchers.IO) {
            poemList.value =  interactor.getStarredPoems()
        }
    }
}