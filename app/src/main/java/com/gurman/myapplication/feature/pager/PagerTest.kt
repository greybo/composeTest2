package com.gurman.myapplication.feature.pager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gurman.myapplication.ui.theme.TestAppThemeWrapper
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerTest() {
    val pagerState = rememberPagerState(pageCount = { 10 })
    val scope = rememberCoroutineScope()
    val currentPage = remember { mutableIntStateOf(0) }

    LaunchedEffect(currentPage.intValue) {
        scope.launch {
            pagerState.animateScrollToPage(currentPage.intValue)
        }
    }
    Column(
        modifier = Modifier
            .padding(top = 34.dp)
            .fillMaxSize()
    ) {
        Row {
            Spacer(modifier = Modifier.width(20.dp))
            ElevatedButton(onClick = {
                if (currentPage.intValue >= 9) {
                    currentPage.intValue = 0
                }
                currentPage.intValue += 1
            }) {
                Text(text = "Button++")
            }
            Spacer(modifier = Modifier.width(20.dp))
            ElevatedButton(onClick = {
                if (currentPage.intValue <= 0) {
                    currentPage.intValue = 10
                }
                currentPage.intValue -= 1
            }) {
                Text(text = "Button--")
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(10f)
        ) { page ->
            Text(
                text = "Page: $page",
                modifier = Modifier
                    .padding(top = 24.dp, start = 16.dp),
            )
        }
        PagerIndicator(pagerState)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPagerTest() = TestAppThemeWrapper {
    PagerTest()
}