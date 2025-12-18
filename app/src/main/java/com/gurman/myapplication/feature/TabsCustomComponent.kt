package com.gurman.myapplication.feature

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gurman.myapplication.feature.modifier.cutOutOutlineBtn
import com.gurman.myapplication.ui.theme.TestAppThemeWrapper
import timber.log.Timber

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabsCustomComponent(listTabsName: List<String>, pagerState: PagerState) {

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = Modifier.wrapContentHeight(),
        containerColor = Color.LightGray,
        indicator = { tabPositions ->
            Timber.e("tabPositions: ${tabPositions[pagerState.currentPage]}")
            tabPositions.mapIndexed { index, tabPosition ->
                Timber.e("tabPositions $index: ${tabPosition}")
            }
            if (pagerState.currentPage < tabPositions.size) {
                val tab = tabPositions[pagerState.currentPage]
                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .tabIndicatorOffset(tab)
                        .height(60.dp)
                        .cutOutOutlineBtn()
                )
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset2(tab)
                    ,
                    height = 60.dp,
                    color = Color.Green.copy(alpha = 0.5f)
                )
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset3(tab)
                    ,
                    height = 60.dp,
                    color = Color.Green.copy(alpha = 0.5f)
                )
            }
        }
    ) {
        listTabsName.forEachIndexed { index, s ->
            Text(
                text = "Tab $index",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp)
            )
        }
    }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.tabIndicatorOffset2(
    currentTabPosition: TabPosition
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "tabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val indicatorOffset by animateDpAsState(
        targetValue = currentTabPosition.right,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.tabIndicatorOffset3(
    currentTabPosition: TabPosition
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "tabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val currentTabWidth by animateDpAsState(
        targetValue = currentTabPosition.right - currentTabPosition.width,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .width(currentTabWidth)
}


@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun PreviewComposeTest() = TestAppThemeWrapper {
    TabsCustomComponent(
        listTabsName = listTabs,
        pagerState = rememberPagerState {listTabs.size }
    )
}

val listTabs = listOf(
    "Tab 1",
    "Tab 2",
    "Tab 3",
    "Tab 4",
)