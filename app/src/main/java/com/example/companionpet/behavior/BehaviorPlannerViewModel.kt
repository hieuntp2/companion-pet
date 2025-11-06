package com.example.companionpet.behavior

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class BehaviorPlannerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BehaviorPlannerUiState())
    val uiState: StateFlow<BehaviorPlannerUiState> = _uiState

    fun onTeachRequested() {
        _uiState.update { state ->
            state.copy(
                recentInteractions = listOf("Bắt đầu chế độ dạy") + state.recentInteractions.take(9),
                currentIntent = "teach"
            )
        }
    }

    fun onIntentRecognized(intent: String, parameters: String? = null) {
        _uiState.update { state ->
            val message = buildString {
                append("Intent: ")
                append(intent)
                if (!parameters.isNullOrBlank()) {
                    append(" (")
                    append(parameters)
                    append(")")
                }
            }
            state.copy(
                recentInteractions = listOf(message) + state.recentInteractions.take(9),
                currentIntent = intent
            )
        }
    }
}
