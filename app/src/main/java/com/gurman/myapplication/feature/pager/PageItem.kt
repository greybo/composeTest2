package com.gurman.myapplication.feature.pager

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun PageItem(index: Int, pageItems: List<Color>) {
    Text(
        text = "Page: $index",
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(pageItems[index])
            .clickable(
//                interactionSource = pageInteractionSource,
//                indication = LocalIndication.current
            ) {
                // Handle page click
            }
            .wrapContentSize(align = Alignment.Center)
    )
}
