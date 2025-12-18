package com.gurman.myapplication.feature.fullscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen

class FullSizeScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        FullSizeLayout() {
            Box(
                Modifier
                    .fillMaxWidth(0.5f)
                    .background(Color.LightGray), contentAlignment = Alignment.Center
            ) {
                Text("Box text", fontSize = 28.sp)
            }
        }
    }
}