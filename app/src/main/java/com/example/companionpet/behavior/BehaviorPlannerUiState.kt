package com.example.companionpet.behavior

import androidx.compose.runtime.Immutable

@Immutable
data class BehaviorPlannerUiState(
    val recentInteractions: List<String> = emptyList(),
    val currentIntent: String? = null
)
