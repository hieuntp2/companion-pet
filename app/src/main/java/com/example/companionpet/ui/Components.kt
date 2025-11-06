package com.example.companionpet.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.companionpet.R
import com.example.companionpet.vision.CameraPreviewState
import com.example.companionpet.vision.VisionOverlayItem

@Composable
fun CommunityPreviewCard(
    modifier: Modifier = Modifier,
    cameraState: CameraPreviewState,
    overlayItems: List<VisionOverlayItem>
) {
    Card(modifier = modifier) {
        when (cameraState) {
            is CameraPreviewState.Active -> CameraPreviewPlaceholder(overlayItems)
            is CameraPreviewState.Loading -> LoadingPreview(message = cameraState.message)
            CameraPreviewState.Unavailable -> PlaceholderContent(textRes = R.string.camera_preview_unavailable)
        }
    }
}

@Composable
private fun LoadingPreview(message: String) {
    PlaceholderContent(
        text = if (message.isNotBlank()) message else "Đang khởi động camera..."
    )
}

@Composable
private fun PlaceholderContent(text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, textAlign = TextAlign.Center, modifier = Modifier.padding(16.dp))
    }
}

@Composable
private fun PlaceholderContent(textRes: Int) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResourceSafe(textRes), textAlign = TextAlign.Center, modifier = Modifier.padding(16.dp))
    }
}

@Composable
private fun CameraPreviewPlaceholder(overlayItems: List<VisionOverlayItem>) {
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            overlayItems.forEach { item ->
                drawBoundingBox(item.boundingBox, label = item.label, confidence = item.confidence)
            }
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawBoundingBox(
    rect: Rect,
    label: String,
    confidence: Float
) {
    val topLeft = Offset(rect.left * size.width, rect.top * size.height)
    val bottomRight = Offset(rect.right * size.width, rect.bottom * size.height)
    drawRect(
        color = Color.Green,
        topLeft = topLeft,
        size = androidx.compose.ui.geometry.Size(bottomRight.x - topLeft.x, bottomRight.y - topLeft.y),
        style = Stroke(width = 4f)
    )
    drawContext.canvas.nativeCanvas.drawText(
        "$label ${(confidence * 100).toInt()}%",
        topLeft.x,
        topLeft.y - 8f,
        android.graphics.Paint().apply {
            color = android.graphics.Color.WHITE
            textSize = 32f
            style = android.graphics.Paint.Style.FILL
        }
    )
}

@Composable
fun CommunicationLog(entries: List<String>, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Nhật ký tương tác",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            if (entries.isEmpty()) {
                Text(text = "Chưa có tương tác", style = MaterialTheme.typography.bodyMedium)
            } else {
                entries.forEach { entry ->
                    Text(text = entry, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }
    }
}

@Composable
fun MemorySummary(entities: List<String>, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Bộ nhớ đã học",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            if (entities.isEmpty()) {
                Text(text = "Chưa ghi nhớ đối tượng nào", style = MaterialTheme.typography.bodyMedium)
            } else {
                entities.forEach { entity ->
                    Text(text = entity, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(vertical = 4.dp))
                }
            }
        }
    }
}

@Composable
private fun stringResourceSafe(resId: Int): String = androidx.compose.ui.res.stringResource(id = resId)
