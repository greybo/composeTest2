package com.gurman.myapplication.feature

import androidx.compose.foundation.BasicTooltipBox
import androidx.compose.foundation.BasicTooltipState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberBasicTooltipState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.gurman.myapplication.R
import com.gurman.myapplication.ui.theme.TestAppThemeWrapper
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BasicTooltip(content: @Composable (BasicTooltipState) -> Unit) {
    val tooltipPosition = TooltipDefaults.rememberPlainTooltipPositionProvider()
    val tooltipState = rememberBasicTooltipState(isPersistent = false)

    BasicTooltipBox(
        positionProvider = tooltipPosition,
        tooltip = {
            Box(
                modifier = Modifier
                    .background(color = colorResource(R.color.purple_50))
                    .padding(24.dp)
            ) {
                Text(
                    text = "Hello World",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        },
        state = tooltipState
    ) {
        content(tooltipState)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
 fun SimpleContent(tooltipState: BasicTooltipState) {
    val scope = rememberCoroutineScope()
    IconButton(onClick = {
        scope.launch { tooltipState.show() }
    }) {
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = "Your icon's description"
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@PreviewLightDark
@Composable
fun PrBasicTooltip() = TestAppThemeWrapper {
        BasicTooltip {
            SimpleContent(it)
        }
    }
