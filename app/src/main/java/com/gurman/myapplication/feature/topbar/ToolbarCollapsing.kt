package com.gurman.myapplication.feature.topbar

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import timber.log.Timber
import kotlin.math.min


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ToolbarCollapsing(propertyTitle: String = "Crown Melbourne", topTitle: String = "What's on at", onNotification: () -> Unit, onSearch: () -> Unit, content: LazyListScope.() -> Unit) {
    val lazyListState = rememberLazyListState()
    val scrollOffsetPx = remember { mutableFloatStateOf(0f) }

    // Вимірюємо початкову висоту розширеного заголовка
    val initialAppBarHeight = 80.dp
    val collapsedAppBarHeight = 60.dp // Висота лише для рядка з "Crown Melbourne"

    val crownMelbourneFontSizeInitial = 22.sp
    val crownMelbourneFontSizeCollapsed = 14.sp

    val scrollRange = with(LocalDensity.current) { initialAppBarHeight.toPx() - collapsedAppBarHeight.toPx() }

    // Відстежуємо загальну прокрутку
    LaunchedEffect(lazyListState) {
        snapshotFlow {
            // Обчислюємо загальний зміщення прокрутки
            val firstItem = lazyListState.layoutInfo.visibleItemsInfo.firstOrNull()
            if (firstItem != null) {
                // Якщо перший елемент - наш Spacer, його розмір і зміщення будуть враховуватися
                // Якщо контент почав прокручуватися, ми хочемо знати, наскільки прокручений Spacer
                val firstVisibleItemIndex = lazyListState.firstVisibleItemIndex
                val firstVisibleItemScrollOffset = lazyListState.firstVisibleItemScrollOffset

                // Якщо перший видимий елемент - це наш Spacer (індекс 0),
                // то його offset - це і є наше зміщення.
                // Якщо ми прокрутили далі, то offset першого елемента буде не 0
                // і ми повинні врахувати, що Spacer вже повністю прокручений.
                if (firstVisibleItemIndex == 0) {
                    firstVisibleItemScrollOffset.toFloat()
                } else {
                    // Якщо Spacer вже не перший видимий елемент, це означає, що він повністю прокручений
                    // І ми вже досягли повного "колапсу" AppBar.
                    scrollRange // Максимальне значення прокрутки для анімації
                }
            } else {
                0f
            }
        }.collect { offset ->
            Timber.w("Collected Scroll Offset: $offset")
            scrollOffsetPx.floatValue = offset
        }
    }

    val scrollProgress = remember {
        derivedStateOf {
            // Прогрес від 0.0 (повністю розгорнутий) до 1.0 (повністю згорнутий)
            min(1f, scrollOffsetPx.floatValue / scrollRange)
        }
    }

    // Анімовані значення для прозорості, масштабу, зсуву
    val textAlpha by animateFloatAsState(
        targetValue = 1f - scrollProgress.value * 2f, // Зникає швидше
        animationSpec = tween(durationMillis = 300)
    )


    val crownMelbourneFontSize by remember {
        derivedStateOf {
            lerp(crownMelbourneFontSizeInitial, crownMelbourneFontSizeCollapsed, scrollProgress.value)
        }
    }

    // Зсув для тексту "Crown Melbourne"
    val crownMelbourneOffsetInitialY = 50.dp
    val crownMelbourneOffsetCollapsedY = with(LocalDensity.current) {
        (collapsedAppBarHeight.toPx() / 2 - crownMelbourneFontSizeCollapsed.toPx() / 2).toDp()
    } // Приблизне кінцеве положення по центру

    val crownMelbourneOffsetY by remember {
        derivedStateOf {
            lerp(crownMelbourneOffsetInitialY, crownMelbourneOffsetCollapsedY, scrollProgress.value)
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val screenWidth = maxWidth

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp)

        ) {
            // Верхній AppBar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(
                        with(LocalDensity.current) {
                            lerp(
                                initialAppBarHeight,
                                collapsedAppBarHeight, // Collapsed bar + icons row
                                scrollProgress.value
                            )
                        }
                    )
                    .background(Color.Black)
            ) {
                // "What's on at" текст
                Text(
                    text = topTitle,
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 16.dp, top = 16.dp)
                        .alpha(textAlpha)
                )
                val offsetX = (with(LocalDensity.current) {
                    lerp(0.dp, (screenWidth / 2 - 100.dp), scrollProgress.value).toPx() // Приблизне центрування
                }).toInt()
                val offsetY = with(LocalDensity.current) { crownMelbourneOffsetY.toPx().toInt() }
                // "Crown Melbourne" текст
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                x = offsetX,
                                y = offsetY
                            )
                        }
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = propertyTitle,
                        color = Color.White,
                        fontSize = crownMelbourneFontSize,
                        fontWeight = FontWeight.Bold,
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Dropdown",
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp)
                            .alpha(textAlpha) // Зникає разом з "What's on at"
                    )
                }

                // Кнопки сповіщень та пошуку
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 8.dp)
                ) {
                    IconButton(onClick = { onNotification() }) {
                        Box {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .offset(x = 12.dp, y = (3).dp)
                                    .size(16.dp)
                                    .background(Color(0xFF007AFF), CircleShape)
                                    .align(Alignment.TopEnd),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "1",
                                    color = Color.White,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                    IconButton(onClick = { onSearch() }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
            // Основний прокручуваний контент
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.fillMaxSize()
            ) {
                // Додайте висоту, яка компенсує прокрутку, щоб контент не перекривався app bar
//                item {
//                    Spacer(modifier = Modifier.height(initialAppBarHeight))
//                }
                content()
//                items(50) {
//                    Text(
//                        text = "Item $it",
//                        color = Color.White,
//                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
//                    )
//                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCrownMelbourneDynamicAppBar() {
    ToolbarCollapsing(
        onNotification = {},
        onSearch = {}
    ) {}
}