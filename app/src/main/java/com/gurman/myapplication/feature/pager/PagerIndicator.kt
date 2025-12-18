package com.gurman.myapplication.feature.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gurman.myapplication.ui.theme.PreviewThemeWrapper
import kotlinx.coroutines.launch


@Composable
fun ColumnScope.PagerIndicator(pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    Row(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .weight(1f)
            .padding(bottom = 8.dp, top = 20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(30.dp)
                    .padding(bottom = 20.dp)
                    .clickable { scope.launch { pagerState.animateScrollToPage(iteration) } }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BoxScope.PagerIndicator(pagerState: PagerState) {
    Row(
        Modifier
//            .fillMaxWidth()
//            .fillMaxHeight()
            .padding(bottom = 8.dp, top = 20.dp)
            .align(Alignment.BottomCenter),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(30.dp)
                    .padding(bottom = 20.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewPagerIndicator() {
    PreviewThemeWrapper(Modifier) {
        PagerIndicator(rememberPagerState { 4 })
    }
}