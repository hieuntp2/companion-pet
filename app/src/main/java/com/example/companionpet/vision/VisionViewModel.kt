package com.example.companionpet.vision

import androidx.compose.ui.geometry.Rect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VisionViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(VisionUiState(cameraState = CameraPreviewState.Loading()))
    val uiState: StateFlow<VisionUiState> = _uiState

    init {
        simulateCameraStart()
    }

    private fun simulateCameraStart() {
        viewModelScope.launch {
            delay(1000)
            _uiState.update { it.copy(cameraState = CameraPreviewState.Active(cameraId = "0")) }
            delay(500)
            _uiState.update {
                it.copy(
                    overlayItems = listOf(
                        VisionOverlayItem(
                            id = "face_1",
                            label = "Khuôn mặt",
                            boundingBox = Rect(0.1f, 0.1f, 0.4f, 0.5f),
                            confidence = 0.92f
                        ),
                        VisionOverlayItem(
                            id = "object_1",
                            label = "Chai",
                            boundingBox = Rect(0.5f, 0.2f, 0.8f, 0.7f),
                            confidence = 0.85f
                        )
                    )
                )
            }
        }
    }
}
