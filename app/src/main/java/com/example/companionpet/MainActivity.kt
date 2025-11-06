package com.example.companionpet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.companionpet.audio.AudioUiState
import com.example.companionpet.audio.AudioViewModel
import com.example.companionpet.behavior.BehaviorPlannerUiState
import com.example.companionpet.behavior.BehaviorPlannerViewModel
import com.example.companionpet.bluetooth.BluetoothUiState
import com.example.companionpet.bluetooth.BluetoothViewModel
import com.example.companionpet.memory.MemoryUiState
import com.example.companionpet.memory.MemoryViewModel
import com.example.companionpet.ui.CommunicationLog
import com.example.companionpet.ui.CommunityPreviewCard
import com.example.companionpet.ui.theme.CompanionPetTheme
import com.example.companionpet.ui.MemorySummary
import com.example.companionpet.vision.VisionUiState
import com.example.companionpet.vision.VisionViewModel

class MainActivity : ComponentActivity() {
    private val audioViewModel: AudioViewModel by viewModels()
    private val bluetoothViewModel: BluetoothViewModel by viewModels()
    private val visionViewModel: VisionViewModel by viewModels()
    private val behaviorPlannerViewModel: BehaviorPlannerViewModel by viewModels()
    private val memoryViewModel: MemoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompanionPetTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val audioState by audioViewModel.uiState.collectAsState()
                    val bluetoothState by bluetoothViewModel.uiState.collectAsState()
                    val visionState by visionViewModel.uiState.collectAsState()
                    val behaviorState by behaviorPlannerViewModel.uiState.collectAsState()
                    val memoryState by memoryViewModel.uiState.collectAsState()

                    CompanionDashboard(
                        uiState = DashboardUiState(
                            audio = audioState,
                            bluetooth = bluetoothState,
                            vision = visionState,
                            behavior = behaviorState,
                            memory = memoryState
                        ),
                        onConnectClick = { bluetoothViewModel.toggleConnection() },
                        onPushToTalkDown = { audioViewModel.onPushToTalkStart() },
                        onPushToTalkUp = { audioViewModel.onPushToTalkStop() },
                        onTeach = { behaviorPlannerViewModel.onTeachRequested() }
                    )
                }
            }
        }
    }
}

private data class DashboardUiState(
    val audio: AudioUiState,
    val bluetooth: BluetoothUiState,
    val vision: VisionUiState,
    val behavior: BehaviorPlannerUiState,
    val memory: MemoryUiState
)

@Composable
private fun CompanionDashboard(
    uiState: DashboardUiState,
    onConnectClick: () -> Unit,
    onPushToTalkDown: () -> Unit,
    onPushToTalkUp: () -> Unit,
    onTeach: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HeaderSection(
            bluetoothUiState = uiState.bluetooth,
            onConnectClick = onConnectClick
        )
        CameraSection(uiState.vision)
        AudioSection(
            audioUiState = uiState.audio,
            onPushToTalkDown = onPushToTalkDown,
            onPushToTalkUp = onPushToTalkUp
        )
        TeachSection(onTeach = onTeach, memoryUiState = uiState.memory)
        CommunicationLog(entries = uiState.behavior.recentInteractions)
    }
}

@Composable
private fun HeaderSection(
    bluetoothUiState: BluetoothUiState,
    onConnectClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = stringResource(id = R.string.app_name), style = MaterialTheme.typography.headlineMedium)
            Text(
                text = bluetoothUiState.connectedDeviceName?.let {
                    stringResource(id = R.string.connected_to_device, it)
                } ?: stringResource(id = R.string.disconnected),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        FilledTonalButton(onClick = onConnectClick) {
            Icon(Icons.Default.Bluetooth, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text(text = stringResource(id = R.string.connect))
        }
    }
}

@Composable
private fun CameraSection(visionUiState: VisionUiState) {
    CommunityPreviewCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
        overlayItems = visionUiState.overlayItems,
        cameraState = visionUiState.cameraState
    )
}

@Composable
private fun AudioSection(
    audioUiState: AudioUiState,
    onPushToTalkDown: () -> Unit,
    onPushToTalkUp: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = if (audioUiState.isListening) stringResource(R.string.listening) else stringResource(R.string.speech_prompt),
            style = MaterialTheme.typography.titleMedium
        )
        IconButton(onClick = onPushToTalkDown) {
            Icon(Icons.Default.Mic, contentDescription = null)
        }
        TextButton(onClick = onPushToTalkUp) {
            Text(text = stringResource(id = R.string.push_to_talk), textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun TeachSection(
    onTeach: () -> Unit,
    memoryUiState: MemoryUiState
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.School, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(id = R.string.teach_prompt), style = MaterialTheme.typography.titleMedium)
        }
        Button(onClick = onTeach) {
            Text(text = stringResource(id = R.string.teach))
        }
        MemorySummary(entities = memoryUiState.knownEntities)
    }
}
