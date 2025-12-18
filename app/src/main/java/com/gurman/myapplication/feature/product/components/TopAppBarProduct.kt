package com.gurman.myapplication.feature.product.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.gurman.myapplication.R
import com.gurman.myapplication.feature.product.data.InvoiceSideType
import com.gurman.myapplication.ui.theme.LocalTheme
import com.gurman.myapplication.ui.theme.PreviewThemeWrapper


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopAppBarProduct(
    sideType: InvoiceSideType,
    title: String,
    onBack: (() -> Unit)? = null,
    action: @Composable RowScope.() -> Unit = {}
) {

    val colors = LocalTheme.current.colors
    TopAppBar(
        modifier = Modifier.shadow(elevation = 8.dp),
        title = {
            Row {
                Text(
                    text = sideType.prefix,
                    fontWeight = FontWeight.Bold,
                    color = sideType.color,
                    modifier = Modifier
                        .background(colors.textDisabled)
                        .padding(start = 4.dp, end = 4.dp)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Text("№${title}", fontWeight = FontWeight.Bold/*, color = Color(0xFF005732)*/)
            }
        },
        navigationIcon = {
            onBack?.let {
                IconButton(onClick = it) { Icon(Icons.Default.ArrowBack, contentDescription = "") }
            }
        },
//        actions = action,
        actions = {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.icon_done_bold),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
//                GifImage()
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colors.surfacePrimaryColor /*Color(0xFFE3E3E3)*/
        )
    )
}

@PreviewLightDark
@Composable
fun PreviewTopBarProduct() = PreviewThemeWrapper {
    TopAppBarProduct(
        sideType = InvoiceSideType.Plus,
        title = "123451234",
        onBack = {})
}