package com.gurman.myapplication.feature.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gurman.myapplication.ui.theme.TestAppThemeWrapper


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerTest2() {
    Column(
        modifier = Modifier
            .padding(top = 34.dp)
            .fillMaxSize()
    ) {
        val pagerState = rememberPagerState(pageCount = { 10 })

        val fling = PagerDefaults.flingBehavior(
            state = pagerState,
            pagerSnapDistance = PagerSnapDistance.atMost(10)
        )

        Column(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(
                state = pagerState,
                pageSize = PageSize.Fixed(300.dp),
                beyondViewportPageCount = 10,
                flingBehavior = fling,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(10f)
            ) { page ->
                Box(
                    modifier = Modifier
                        .padding(top = 24.dp/* start = 16.dp*/)
                        .padding(horizontal = 8.dp)
                        .fillMaxSize()
                        .background(color = Color.LightGray)
                ) {
                    Text(text = "Page: $page")
                }
            }
            PagerIndicator(pagerState)

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPagerTest2() = TestAppThemeWrapper {
    PagerTest2()
}