package com.gurman.myapplication.feature.product

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.Text
import cafe.adriel.voyager.core.screen.Screen

class ProductTestScree : Screen {
    @Composable
    override fun Content() {
        Text("Hello test screen!")
    }
}