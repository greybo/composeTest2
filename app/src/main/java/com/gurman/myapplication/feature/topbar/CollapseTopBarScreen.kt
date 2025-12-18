package com.gurman.myapplication.feature.topbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen

class CollapseTopBarScreen : Screen {
    @Composable
    override fun Content() {
        ToolbarCollapsing(onNotification = {}, onSearch = {}) {
            items(50) {
                Text(
                    text = "Item $it",
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
//        CollapsingToolbar2()
    }
}