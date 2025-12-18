package com.gurman.myapplication.feature.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gurman.myapplication.feature.product.components.TopAppBarProduct
import com.gurman.myapplication.feature.product.data.InvoiceItem
import com.gurman.myapplication.feature.product.data.invoiceItem
import com.gurman.myapplication.ui.theme.AppScreenPreview
import com.gurman.myapplication.ui.theme.ThemePreviews

@ThemePreviews
@Composable
private fun PrivacyScreen() = AppScreenPreview(InvoiceProductScreen3())

class InvoiceProductScreen3(private val invoice: InvoiceItem = invoiceItem) : Screen {
    private val settings = invoice.settings

    @Composable
    override fun Content() {
        val products = invoice.products
        val navigator = LocalNavigator.currentOrThrow
        Scaffold(
            topBar = {
                TopAppBarProduct(
                    sideType = invoice.sideType,
                    title = invoice.id,
                    onBack = { navigator.pop() },
                    action = {}
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                products.map {
                    ProductHolder(it, moreDetails = false,
                        onEditMore = if (settings.previewScreen) {
                            {
                                navigator.push(InvoiceProductDetailsScreen())
                            }
                        } else null,
                        onPlace = {},
                        onAmount = {})
                }
            }
        }
    }
}
