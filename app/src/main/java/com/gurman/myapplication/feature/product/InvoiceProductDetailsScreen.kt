package com.gurman.myapplication.feature.product

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class InvoiceProductDetailsScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        ProductMoreLayout() {
            navigator.push(ProductTestScree())
        }
    }
}