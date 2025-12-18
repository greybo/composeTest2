package com.gurman.myapplication.feature.picker.pager

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter


@Preview
@Composable
fun CustomSnapDistance() {
    // [START android_compose_pager_custom_snap_distance]
    val pagerState = rememberPagerState(pageCount = { 10 })

    val fling = PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(10)
    )

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            pageSize = PageSize.Fixed(200.dp),
            beyondViewportPageCount = 10,
            flingBehavior = fling
        ) {
            PagerSampleItem(page = it)
        }
    }
    // [END android_compose_pager_custom_snap_distance]
}

@Composable
internal fun PagerSampleItem(
    page: Int,
    modifier: Modifier = Modifier,
) {
    Box(modifier.fillMaxSize()) {
        // Our page content, displaying a random image
        Image(
            painter = rememberAsyncImagePainter(model = rememberRandomSampleImageUrl(width = 600)),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier.matchParentSize()
        )

        // Displays the page index
        Text(
            text = page.toString(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(4.dp))
                .sizeIn(minWidth = 40.dp, minHeight = 40.dp)
                .padding(8.dp)
                .wrapContentSize(Alignment.Center)
        )
    }
}


private val rangeForRandom = (0..100000)

fun randomSampleImageUrl(
    seed: Int = rangeForRandom.random(),
    width: Int = 300,
    height: Int = width,
): String {
    return "https://picsum.photos/seed/$seed/$width/$height"
}

/**
 * Remember a URL generate by [randomSampleImageUrl].
 */
@Composable
fun rememberRandomSampleImageUrl(
    seed: Int = rangeForRandom.random(),
    width: Int = 300,
    height: Int = width,
): String = remember { randomSampleImageUrl(seed, width, height) }