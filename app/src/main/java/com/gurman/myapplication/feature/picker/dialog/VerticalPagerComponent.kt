package com.gurman.myapplication.feature.picker.dialog


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gurman.myapplication.ui.theme.LocalTheme
import com.gurman.myapplication.ui.theme.TestAppTextStyles.HeadlineSemiBold
import com.gurman.myapplication.ui.theme.TestAppThemeWrapper
import timber.log.Timber


@SuppressLint("AutoboxingStateValueProperty")
@Composable
fun VerticalPagerComponent2(
    count: Int = 31,
    selected: Int = 25,
    suffix: String = "",
    enabled: Boolean = true,
    callback: (Int) -> Unit = {},
) {

    val maxCount = count * 200
    val selectCenter = selected + count * 100
    val pagerState = rememberPagerState(pageCount = { maxCount })
    val colors = LocalTheme.current.colors
    val colorText = if (enabled) colors.textCtaTertiary else colors.textDisabled

    val heightCell = 70.dp
    val widthCell = 100.dp

    val fling = PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(10)
    )

    LaunchedEffect(Unit) {
        Timber.e("PickerDate LaunchedEffect scrollToPag first scroll $suffix")
        pagerState.scrollToPage(selectCenter)
    }

    Column(
        modifier = Modifier.width(widthCell)
    ) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier.height(heightCell),
            userScrollEnabled = enabled,
            flingBehavior = fling,
        ) { position ->

            val page = position % count
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$page $suffix",
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    style = HeadlineSemiBold,
                    color = colorText
                )
            }
        }
    }

    LaunchedEffect(enabled) {
        if (!enabled) {
            Timber.e("PickerDate LaunchedEffect enabled=$enabled, selected=$selected")
            pagerState.scrollToPage(count * 100)
        }
    }
    val result = pagerState.currentPage % count
    Timber.e("PickerDate result callback=${result} $suffix")
    callback(result)
}


@Preview
@Composable
fun PreviewVerticalPagerComponent() = TestAppThemeWrapper {
    VerticalPagerComponent2(count = 14, 0, "min", enabled = false) {}
}