package com.gurman.myapplication.feature.pager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gurman.myapplication.ui.theme.PreviewThemeWrapper


@Composable
fun TabsIndicatorLine4(tabsList: List<String> = listTabs) {
//    val tabsList = listOf("Tab 1", "Tab 2", "Tab 3")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabsList.forEachIndexed { index, title ->
               Box( modifier = Modifier
                   .padding(horizontal = 4.dp)
                   .padding(bottom = 8.dp)
               ) {
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(title, maxLines = 2)
                        },
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.Cyan)
                    )
                }
            }
        }

        when (selectedTabIndex) {
            0 -> {
                Text("Вміст для Tab 1")
            }

            1 -> {
                Text("Вміст для Tab 2")
            }

            2 -> {
                Text("Вміст для Tab 3")
            }

            3 -> {
                Text("Вміст для Tab 4")
            }
        }
    }
}


@Preview
@Composable
fun PreviewTabsIndicatorLine4() = PreviewThemeWrapper(
    modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
) {
    TabsIndicatorLine4(
//        listTabsName = listTabs,
//        pagerState = rememberPagerState { listTabs.size }
    )
}

val listTabs = listOf(
    "0\nВідправку",
    "2\nКомплектуеться",
    "3\nTabs",
    "4\nTabs",
)