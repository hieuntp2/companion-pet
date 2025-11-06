package com.example.companionpet.vision

import androidx.compose.runtime.Immutable
import androidx.compose.ui.geometry.Rect

@Immutable
data class VisionOverlayItem(
    val id: String,
    val label: String,
    val boundingBox: Rect,
    val confidence: Float
)

@Immutable
data class VisionUiState(
    val cameraState: CameraPreviewState = CameraPreviewState.Unavailable,
    val overlayItems: List<VisionOverlayItem> = emptyList()
)

sealed interface CameraPreviewState {
    data object Unavailable : CameraPreviewState
    data class Active(val cameraId: String) : CameraPreviewState
    data class Loading(val message: String = "") : CameraPreviewState
}
