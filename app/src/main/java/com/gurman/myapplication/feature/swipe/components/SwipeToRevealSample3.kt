//package com.greybo.warehouse.compose.screen.invoice2.a_conponent.swipe
//
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Refresh
//import androidx.compose.material.icons.outlined.Delete
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
////import androidx.wear.compose.foundation.rememberRevealState
////import androidx.wear.compose.material3.Icon
////import androidx.wear.compose.material3.SwipeToReveal
////import androidx.wear.compose.material3.SwipeToRevealDefaults
////import androidx.wear.compose.material3.Text
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import timber.log.Timber
//
////https://composables.com/wear-compose-material3/swipetoreveal
//
//@Composable
//fun SwipeToRevealSample3(
//    onDelete: () -> Unit,
//    content: @Composable () -> Unit,
//) {
//
//    val scope = rememberCoroutineScope()
//    var deleteJob: Job? = null
//    val expandedState = remember { mutableStateOf(true) }
//
//    fun delayDelete() {
//        deleteJob = scope.launch {
//            delay(2000)
//            expandedState.value = false
//            delay(300)
//            withContext(Dispatchers.Main) { onDelete() }
//        }
//    }
//
//    AnimatedVisibility(
//        visible = expandedState.value,
//        modifier = Modifier
//    ) {
//        SwipeToReveal(
//            // Use the double action anchor width when revealing two actions
//            revealState = rememberRevealState(
//                confirmValueChange = {
//                    Timber.w("confirmValueChange reveal value=${it.value}")
//                    it.value < 2
//                },
//                anchors = SwipeToRevealDefaults.anchors(
//                    anchorWidth = SwipeToRevealDefaults.DoubleActionAnchorWidth,
//                )
//            ),
//            actions = {
//                primaryAction(
//                    onClick = { delayDelete() },
//                    icon = { Icon(Icons.Outlined.Delete, contentDescription = "Delete") },
//                    text = { Text("Delete") },
//                    containerColor = Color.Red,
//                    contentColor = Color.White
//                )
////            secondaryAction(
////                onClick = { /* This block is called when the secondary action is executed. */ },
////                icon = { Icon(Icons.Outlined.MoreVert, contentDescription = "Options") }
////            )
//                undoPrimaryAction(
//                    onClick = { deleteJob?.cancel() },
////                onClick = { /* This block is called when the undo primary action is executed. */ },
//                    text = { Text("Undo") },
//                    icon = { Icon(Icons.Default.Refresh, contentDescription = "Undo") },
//                )
//            },
//            content = content
//        )
//    }
//}