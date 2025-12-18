package com.gurman.myapplication.feature.swipe.components

//import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.TransformingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberTransformingLazyColumnState
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.lazy.rememberTransformationSpec

//@Preview
@Composable
fun SwipeToRevealWithTransformingLazyColumnSample() {
    val transformationSpec = rememberTransformationSpec()
    val tlcState = rememberTransformingLazyColumnState(1,1)

    TransformingLazyColumn(
        state = tlcState,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
        modifier = Modifier.background(Color.Transparent)
    ) {
        items(
            count = 1,
//            key = { it.hashCode() }
        ) { index ->
//            val revealState = rememberRevealState(
//                anchors = SwipeToRevealDefaults.anchors(
//                    anchorWidth = SwipeToRevealDefaults.DoubleActionAnchorWidth,
//                )
//            )
//
//            // SwipeToReveal is covered on scroll.
//            LaunchedEffect(tlcState.isScrollInProgress) {
//                if (tlcState.isScrollInProgress && revealState.currentValue != RevealValue.Covered) {
//                    revealState.animateTo(targetValue = RevealValue.Covered)
//                }
//            }
            Text("SwipeToRevealWithTransformingLazyColumnSample", fontSize = 22.sp, color = Color.Blue)
//            SwipeToReveal(
//                revealState = revealState,
//                modifier = Modifier
//                    .transformedHeight { items, transformationSpec -> 1 }
//                    .graphicsLayer {
//                        with(transformationSpec) { applyContainerTransformation(scrollProgress) }
//                        // Is needed to disable clipping.
//                        compositingStrategy = CompositingStrategy.ModulateAlpha
//                        clip = false
//                    },
//                actions = {
//                    primaryAction(
//                        onClick = { /* Called when the primary action is executed. */ },
//                        icon = { Icon(Icons.Outlined.Delete, contentDescription = "Delete") },
//                        text = { Text("Delete") }
//                    )
//                }
//            ) {
//                Text("SwipeToRevealWithTransformingLazyColumnSample", fontSize = 22.sp, color = Color.Blue)
////                TransformExclusion {
////                    TitleCard(
////                        onClick = {},
////                        title = { Text("Message #$index") },
////                        subtitle = { Text("Body of the message") },
////                        modifier = Modifier.semantics {
////                            // Use custom actions to make the primary action accessible
////                            customActions = listOf(
////                                CustomAccessibilityAction("Delete") {
////                                    /* Add the primary action click handler here */
////                                    true
////                                },
////                            )
////                        }
////                    )
////                }
//            }
        }
    }
}