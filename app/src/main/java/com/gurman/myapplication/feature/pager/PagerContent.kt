package com.gurman.myapplication.feature.pager

import androidx.compose.ui.graphics.Color


val listTabsName
    get() = List(colorList.size) { index ->
        "Tab $index"
    }
val colorList = listOf(
//    Color.DarkGray,
    Color.Magenta,
    Color.Red,
    Color.Blue,
    Color.Cyan,
//    Color.Green,
)