package com.example.companionpet.memory

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MemoryViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MemoryUiState())
    val uiState: StateFlow<MemoryUiState> = _uiState

    fun addFaceLabel(label: String) {
        _uiState.update { state ->
            val updated = (state.knownEntities + "Khuôn mặt: $label").distinct()
            state.copy(knownEntities = updated)
        }
    }

    fun addObjectLabel(label: String) {
        _uiState.update { state ->
            val updated = (state.knownEntities + "Đồ vật: $label").distinct()
            state.copy(knownEntities = updated)
        }
    }
}
