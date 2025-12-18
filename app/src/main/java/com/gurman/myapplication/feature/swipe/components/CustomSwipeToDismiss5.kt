package com.gurman.myapplication.feature.swipe.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.math.roundToInt


@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun MySwipeToDismiss5(
    onDelete: () -> Unit,
    content: @Composable BoxScope.(Boolean) -> Unit
) {

    val size = remember { mutableStateOf<Size?>(null) }

    // Create our own state to control the position of the element
    var isHalfwayPosition by remember { mutableStateOf(false) }
    var isConfirmButtonVisible by remember { mutableStateOf(false) }
    var isDeleted by remember { mutableStateOf(false) }
    val expandableState = remember { mutableStateOf(true) }

    val scope = rememberCoroutineScope()

    // Create a state for the swipe animation
    val swipeableState = rememberSwipeableState(
        initialValue = 0,
        confirmStateChange = { newValue ->
            Timber.w("confirm state change: $newValue")
            isConfirmButtonVisible = newValue == 2
            // Allowed state changes
            newValue <= 1
        }
    )

    fun resetSwipe() {
        isConfirmButtonVisible = false
        scope.launch {
            swipeableState.animateTo(0)
        }
    }

    fun delayDelete() {
        isDeleted = true
        expandableState.value = false
        resetSwipe()
        scope.launch {
            delay(600)
            withContext(Dispatchers.Main) { onDelete() }
        }
    }

    val anchors = size.value?.let {
        // Determine the dimensions for the swipe.
        // Initial positions
        mapOf(0f to 0, (it.width * 0.45).toFloat() to 1, it.width to 2)
    }


    // Track the current position for centering
    LaunchedEffect(swipeableState.currentValue) {
        isHalfwayPosition = swipeableState.currentValue >= 1
        Timber.w("swipeableState.currentValue: ${swipeableState.currentValue}, isConfirmVisible=${isConfirmButtonVisible}")

        if (swipeableState.currentValue == 2) {
            isConfirmButtonVisible = !isDeleted && true
            swipeableState.animateTo(1)
        }
        if (swipeableState.currentValue == 0) {
            isConfirmButtonVisible = false
        }
    }

    LaunchedEffect(isConfirmButtonVisible) {
        if (isConfirmButtonVisible) {
            Timber.w("LaunchedEffect animateTo: 1")
            swipeableState.animateTo(1)
        }
    }

    AnimatedVisibility(
        visible = expandableState.value,
        modifier = Modifier
    ) {
        Box {
            // Background content (delete and back buttons)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .onSizeChanged { size.value = it.toSize() },
                contentAlignment = Alignment.CenterStart
            ) {
                // Delete button visible on swipe
                if (swipeableState.offset.value > 150f) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (!isConfirmButtonVisible)
                        // Delete button
                            IconButton(
                                onClick = { isConfirmButtonVisible = true },
                                modifier = Modifier.padding(start = 16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "icon Видалити",
                                    tint = Color.Black
                                )
                            }
                    }
                }
            }

            // Delete confirmation button, visible only in half-slider state
            AnimatedVisibility(
                visible = isConfirmButtonVisible,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 16.dp)
            ) {

                // Cancel button below Delete button
                Box {
                    Button(
                        onClick = { delayDelete() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Видалити", color = Color.White)
                    }

                    Button(
                        onClick = { resetSwipe() },
                        modifier = Modifier.padding(top = 50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                    ) {
                        Text("Скасувати", color = Color.Black)
                    }
                }
            }

            // Main content with swipe capability
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .run {
                        anchors?.let {
                            this
                                .offset {
                                    IntOffset(swipeableState.offset.value.roundToInt(), 0)
                                }
                                .swipeable(
                                    state = swipeableState,
                                    anchors = anchors,
                                    thresholds = { _, _ -> FractionalThreshold(0.3f) }, // Threshold for transition between states
                                    orientation = Orientation.Horizontal
                                )
                        } ?: this
                    }
//                    .background(MaterialTheme.colorScheme.surface)
                ,
                content = { content(!isHalfwayPosition) }
            )
        }
    }
}