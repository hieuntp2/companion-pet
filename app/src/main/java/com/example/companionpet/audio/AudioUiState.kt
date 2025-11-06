package com.example.companionpet.audio

import androidx.compose.runtime.Immutable

@Immutable
data class AudioUiState(
    val isListening: Boolean = false,
    val lastTranscription: String? = null,
    val vadEnabled: Boolean = false
)
