package com.gurman.myapplication.feature.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.gurman.myapplication.feature.TabsCustomComponent
import com.gurman.myapplication.ui.theme.PreviewThemeWrapper
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerTest3() {
    val pagerState = rememberPagerState { colorList.size }
    Column {
        TabsCustomComponent(listTabsName, pagerState)
        HorizontalPager(state = pagerState) { page ->
            Card(
                Modifier
//                .size(300.dp)
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .graphicsLayer {
                        // Calculate the absolute offset for the current page from the
                        // scroll position. We use the absolute value which allows us to mirror
                        // any effects for both directions
                        val pageOffset = (
                                (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                                ).absoluteValue

                        // We animate the alpha, between 50% and 100%
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            ) {
                PageItem(
                    index = page,
                    pageItems = colorList
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewPagerTest3() = PreviewThemeWrapper {
    PagerTest3()
}