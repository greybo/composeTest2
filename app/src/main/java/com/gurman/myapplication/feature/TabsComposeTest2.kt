package com.gurman.myapplication.feature

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gurman.myapplication.feature.pager.listTabsName
import com.gurman.myapplication.ui.theme.TestAppThemeWrapper

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TabsComposeTest2(listTabsName: List<String>, pagerState: PagerState) {
    TabRow(
        selectedTabIndex = pagerState.currentPage,
//        modifier = TODO(),
//        containerColor = TODO(),
//        contentColor = TODO(),
//        indicator = TODO(),
//        divider = TODO(),
        tabs = {
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
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun PreviewComposeTest2() = TestAppThemeWrapper {
    TabsComposeTest2(
        listTabsName = listTabsName,
        pagerState = rememberPagerState {listTabsName.size }
    )
}
