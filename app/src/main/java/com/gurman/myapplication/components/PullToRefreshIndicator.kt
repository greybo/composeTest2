package com.gurman.myapplication.components


import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.PositionalThreshold
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefreshIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshIndicator(
    state: PullToRefreshState,
    isRefreshing: Boolean,
    modifier: Modifier = Modifier,
    containerColor: Color = PullToRefreshDefaults.containerColor,
    color: Color = PullToRefreshDefaults.indicatorColor,
    threshold: Dp = PositionalThreshold,
) {
    Box(
        modifier =
            modifier.pullToRefreshIndicator(
                state = state,
                isRefreshing = isRefreshing,
                containerColor = containerColor,
                threshold = threshold,
            ),
        contentAlignment = Alignment.Center
    ) {
        Crossfade(
            targetState = isRefreshing,
            animationSpec = tween(durationMillis = 100)
        ) { refreshing ->
            if (refreshing) {
                CircularProgressIndicator(
                    strokeWidth = 2.5.dp,
                    color = color,
                    modifier = Modifier.size(16.dp),
                )
            } else {
                CircularArrowProgressIndicator1111(
                    progress = { state.distanceFraction },
                    color = color,
                )
            }
        }
    }
}

@Composable
fun CircularArrowProgressIndicator1111(progress: () -> Float, color: Color) {

}
