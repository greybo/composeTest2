//package com.gurman.myapplication.components.pager
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.pager.HorizontalPager
//import androidx.compose.foundation.pager.rememberPagerState
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.tooling.preview.Preview
//import com.gurman.myapplication.ui.theme.PreviewThemeWrapper
//
//@Composable
//fun PagerTest4() {
//    val pagerState = rememberPagerState(pageCount = {
//        colorList.size
//    })
//    Column {
//        TabsIndicatorLine4(listTabsName, /*pagerState*/)
//        HorizontalPager(state = pagerState) {}
//    }
//}
//
//
//@Preview
//@Composable
//fun PreviewPagerTest4() = PreviewThemeWrapper {
//    PagerTest4()
//}