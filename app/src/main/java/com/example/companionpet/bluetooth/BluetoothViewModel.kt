package com.example.companionpet.bluetooth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class BluetoothViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BluetoothUiState())
    val uiState: StateFlow<BluetoothUiState> = _uiState

    fun toggleConnection() {
        _uiState.update { state ->
            if (state.connectedDeviceName == null) {
                state.copy(connectedDeviceName = "HC-05", isConnecting = false)
            } else {
                state.copy(connectedDeviceName = null, isConnecting = false)
            }
        }
    }
}
