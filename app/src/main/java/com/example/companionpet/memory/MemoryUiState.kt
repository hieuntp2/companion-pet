package com.example.companionpet.memory

import androidx.compose.runtime.Immutable

@Immutable
data class MemoryUiState(
    val knownEntities: List<String> = emptyList()
)
