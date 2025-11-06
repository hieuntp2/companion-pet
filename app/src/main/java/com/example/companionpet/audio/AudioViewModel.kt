package com.example.companionpet.audio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AudioViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AudioUiState())
    val uiState: StateFlow<AudioUiState> = _uiState

    fun onPushToTalkStart() {
        _uiState.update { it.copy(isListening = true) }
        simulateStreamingTranscription()
    }

    fun onPushToTalkStop() {
        _uiState.update { it.copy(isListening = false) }
    }

    private fun simulateStreamingTranscription() {
        viewModelScope.launch {
            delay(1500)
            _uiState.update { it.copy(lastTranscription = "Xin chào, mình có thể giúp gì?", isListening = false) }
        }
    }
}
