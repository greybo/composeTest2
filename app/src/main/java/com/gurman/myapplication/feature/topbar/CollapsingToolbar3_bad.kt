package com.gurman.myapplication.feature.topbar

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.mutableStateOf
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

// Вам потрібно буде додати ці ресурси до вашого проекту в res/drawable
// Для прикладу я використовую іконки-плейсхолдери.
// @DrawableRes
// val ic_hotel: Int = R.drawable.ic_hotel // Замініть на ваш реальний ID ресурсу
// @DrawableRes
// val ic_eat_drink: Int = R.drawable.ic_eat_drink
// @DrawableRes
// val ic_casino: Int = R.drawable.ic_casino
// @DrawableRes
// val ic_entertainment: Int = R.drawable.ic_entertainment

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun CollapsingToolbar3_bad() {
    val lazyListState = rememberLazyListState()
    val scrollOffsetPx = remember { mutableStateOf(0f) }

    // Вимірюємо початкову висоту розширеного заголовка
    val initialAppBarHeight = 60.dp // Це висота тільки для "What's on at" і "Crown Melbourne"
    val collapsedAppBarHeight = 56.dp // Висота для згорнутого стану "Crown Melbourne"
    val iconsRowHeight = 80.dp // Висота рядка з іконками (навіть якщо він не використовується, зберігаємо для повноти)

    // Максимальний діапазон прокрутки для анімації переходу
    // Це та кількість пікселів, яку потрібно прокрутити,
    // щоб AppBar повністю перейшов у згорнутий стан.
    val scrollRange = with(LocalDensity.current) { (initialAppBarHeight - 16.dp).toPx() } // Залишаємо запас, щоб "What's on at" встиг зникнути


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
            scrollOffsetPx.value = offset
        }
    }


    val scrollProgress = remember {
        derivedStateOf {
            // Прогрес від 0.0 (повністю розгорнутий) до 1.0 (повністю згорнутий)
            min(1f, scrollOffsetPx.value / scrollRange)
        }
    }
    Timber.w("Scroll Progress: ${scrollProgress.value}")


    // Анімовані значення для прозорості, масштабу, зсуву
    val textAlpha by animateFloatAsState(
        targetValue = 1f - scrollProgress.value * 2f, // Зникає швидше
        animationSpec = tween(durationMillis = 300)
    )

    val crownMelbourneFontSizeInitial = 22.sp
    val crownMelbourneFontSizeCollapsed = 14.sp

    val crownMelbourneFontSize by remember {
        derivedStateOf {
            lerp(crownMelbourneFontSizeInitial, crownMelbourneFontSizeCollapsed, scrollProgress.value)
        }
    }

    // Зсув для тексту "Crown Melbourne"
    // Виправлення: початкова позиція повинна бути відносно верхнього краю батьківського Box
    val crownMelbourneOffsetInitialY = 16.dp + 24.dp // top padding + height of "What's on at" approx
    val crownMelbourneOffsetCollapsedY = with(LocalDensity.current) {
        (collapsedAppBarHeight.toPx() / 2 - crownMelbourneFontSizeCollapsed.toPx() / 2).toDp()
    } // Приблизне кінцеве положення по центру

    val crownMelbourneOffsetY by remember {
        derivedStateOf {
            lerp(crownMelbourneOffsetInitialY, crownMelbourneOffsetCollapsedY, scrollProgress.value)
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val screenWidth = maxWidth

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            // Верхній AppBar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(
                        with(LocalDensity.current) {
                            // Висота AppBar змінюється від initialAppBarHeight до collapsedAppBarHeight
                            lerp(
                                initialAppBarHeight,
                                collapsedAppBarHeight,
                                scrollProgress.value
                            )
                        }
                    )
                    .background(Color.Black)
            ) {
                // "What's on at" текст
                Text(
                    text = "What's on at",
                    color = Color.White,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 16.dp, top = 16.dp)
                        .alpha(textAlpha)
                )

                // Кнопки сповіщень та пошуку
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 8.dp) // Зберігаємо початковий padding для кнопок
                ) {
                    IconButton(onClick = { /* Handle notification click */ }) {
                        Box {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                            Box(
                                modifier = Modifier
                                    .offset(x = 12.dp, y = (-8).dp)
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
                    IconButton(onClick = { /* Handle search click */ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                val targetX = with(LocalDensity.current) {
                    // Обчислюємо зміщення для центрування тексту
                    val textWidthPx = if (scrollProgress.value == 1f) {
                        // Приблизне обчислення ширини тексту при колапсі
                        // Це складний момент, краще використовувати onSizeChanged для точного вимірювання
                        // або заздалегідь знати ширину. Тут - груба оцінка.
                        crownMelbourneFontSizeCollapsed.toPx() * "Crown Melbourne".length / 2 // Дуже груба оцінка
                    } else {
                        0f // При розгорнутому стані не зміщуємо по X
                    }

                    val centeredX = (screenWidth.toPx() - textWidthPx) / 2
                    val initialX = 16.dp.toPx() // Початкове ліве вирівнювання

                    lerp(
                        initialX.toDp(),
                        centeredX.toDp(),
                        scrollProgress.value
                    ).toPx()
                }
                val targetY = with(LocalDensity.current) { crownMelbourneOffsetY.toPx() }.toInt()
                // "Crown Melbourne" текст і стрілка
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                x = targetX.toInt(),
                                y = targetY
                            )
                        }
                    // Знімаємо padding тут, бо offset вже його враховує, щоб уникнути подвійного зміщення
                    // .padding(start = 16.dp)
                ) {
                    Text(
                        text = "Crown Melbourne",
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
            }

            // Основний прокручуваний контент
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.fillMaxSize()
            ) {
                // Додайте висоту, яка компенсує прокрутку, щоб контент не перекривався app bar
                item {
                    // Цей Spacer буде прокручуватися, дозволяючи AppBar колапсувати
                    Spacer(modifier = Modifier.height(initialAppBarHeight + iconsRowHeight))
                }
                items(50) {
                    Text(
                        text = "Item $it",
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCollapsingToolbar3_bad() {
    CollapsingToolbar3_bad()
}