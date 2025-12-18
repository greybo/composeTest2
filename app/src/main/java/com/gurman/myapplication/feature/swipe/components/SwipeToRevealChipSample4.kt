package com.gurman.myapplication.feature.swipe.components


//import androidx.compose.ui.Modifier
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.RevealValue
import androidx.wear.compose.material.SwipeToRevealChip
import androidx.wear.compose.material.SwipeToRevealPrimaryAction
import androidx.wear.compose.material.SwipeToRevealUndoAction
import androidx.wear.compose.material.rememberRevealState
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.Text
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

@OptIn(ExperimentalWearMaterialApi::class, ExperimentalWearMaterialApi::class)
@Composable
fun SwipeToRevealChipSample4(onDelete: () -> Unit, content: @Composable () -> Unit) {

    val revealState = rememberRevealState()
    val scope = rememberCoroutineScope()
    val expandedState = remember { mutableStateOf(true) }

    var deleteJob: Job? by remember { mutableStateOf(null) }

    fun delayDelete() {
        deleteJob = scope.launch {
            delay(2000)
            expandedState.value = false
            delay(300)
            withContext(Dispatchers.Main) { onDelete() }
        }
    }

    LaunchedEffect(revealState.currentValue) {
        Timber.w("LaunchedEffect value=${revealState.currentValue.value}")
    }

    AnimatedVisibility(
        visible = expandedState.value,
        modifier = Modifier
    ) {
        SwipeToRevealChip(
            revealState = revealState,
            primaryAction = {
                SwipeToRevealPrimaryAction(
                    revealState = revealState,
                    icon = { Icon(Icons.Filled.Delete, "Delete") },
                    label = { Text("Delete") },
                    onClick = {
                        scope.launch {
                            revealState.animateTo(RevealValue.RightRevealed)
                            delayDelete()
                        }
                    }
                )
            },

            undoPrimaryAction = {
                SwipeToRevealUndoAction(
                    revealState = revealState,
                    label = { Text("Undo", modifier = Modifier.fillMaxWidth(0.5f), textAlign = TextAlign.Center) },
                    icon = { Icon(Icons.Filled.Refresh, "undo") },
                    onClick = {
                        scope.launch {
                            deleteJob?.cancel()
                            revealState.animateTo(RevealValue.Covered)
                        }
                    }
                )
            },

            onFullSwipe = { /* Add the full swipe handler here */ },
            content = content
        )
    }
}