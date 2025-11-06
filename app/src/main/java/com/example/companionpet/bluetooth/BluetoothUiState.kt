package com.example.companionpet.bluetooth

import androidx.compose.runtime.Immutable

@Immutable
data class BluetoothUiState(
    val connectedDeviceName: String? = null,
    val isConnecting: Boolean = false,
    val availableDevices: List<String> = emptyList()
)
