package com.gurman.myapplication.feature.picker

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.gurman.myapplication.feature.picker.DataPickerTexts.monthsMock
import timber.log.Timber
import kotlin.math.absoluteValue

const val additionalNumberFields: Int = 1

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun WheelPickerCell(items: List<String> = monthsMock, selected: String = "", onChangeValue: (String) -> Unit = {}) {
    val maxPage = 100 * items.size
    val selectedValue = remember { mutableStateOf(selected) }
    val pagerState = rememberPagerState(pageCount = { maxPage })
    Column {
        Spacer(Modifier.height(50.dp))

        VerticalPager(
            modifier = Modifier.size(height = 150.dp, width = 70.dp),
            state = pagerState,
            pageSize = numberPagesPerViewport,
        ) { page ->
            val dataIndex = page % items.size
            val item = items[dataIndex]
            Card(
                Modifier
                    .padding(4.dp)
                    .graphicsLayer {
                        val pageOffset = ((pagerState.currentPage - page + additionalNumberFields) + pagerState.currentPageOffsetFraction).absoluteValue
                        alpha = lerp(
                            start = 0.2f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }) {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Text(item)
                }
            }

            LaunchedEffect(pagerState.currentPage) {
                val currentPage = (pagerState.currentPage % items.size) + 1
                val index = if (currentPage >= items.size) 0 else currentPage
                onChangeValue(items[index])//items[index]
            }
        }
    }

    LaunchedEffect(Unit) {
        val index = items.indexOf(selectedValue.value).let { if (it == -1) 0 else it }
        Timber.w("PickerDateScreen: select index $index, value = ${selectedValue.value}")

        pagerState.scrollToPage(maxPage / 2 + index - additionalNumberFields)
    }
}

private val numberPagesPerViewport = object : PageSize {
    override fun Density.calculateMainAxisPageSize(
        availableSpace: Int,
        pageSpacing: Int,
    ): Int {
        return (availableSpace - 2 * pageSpacing) / (additionalNumberFields * 2 + 1)
    }
}



