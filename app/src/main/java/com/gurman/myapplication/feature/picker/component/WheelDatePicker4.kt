package com.gurman.myapplication.feature.picker.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.util.Locale
import kotlin.math.abs


@Preview
@Composable
fun WheelDatePicker4(
    initialDate: LocalDate = LocalDate.now(),
    onDateChanged: (LocalDate) -> Unit = {},
    modifier: Modifier = Modifier,
    yearRange: IntRange = 1920..2100,
) {
    var selectedDate by remember { mutableStateOf(initialDate) }

    // Списки для вибору
    val years = remember { yearRange.map { it.toString() } }

    // --- ВИПРАВЛЕНИЙ БЛОК ---
    val months = remember {
        Month.entries.map {
            it.getDisplayName(java.time.format.TextStyle.FULL, Locale("uk", "UA"))
                .replaceFirstChar(Char::titlecase)
        }
    }

    // Динамічний список днів, що залежить від року та місяця
    val daysInMonth = remember(selectedDate.year, selectedDate.month) {
        val yearMonth = YearMonth.of(selectedDate.year, selectedDate.month)
        (1..yearMonth.lengthOfMonth()).map { it.toString() }
    }

    // Коригування дати, якщо вона стала невалідною (напр. 31 лютого)
    LaunchedEffect(selectedDate) {
        val correctedDay = selectedDate.dayOfMonth.coerceAtMost(daysInMonth.size)
        val correctedDate = selectedDate.withDayOfMonth(correctedDay)
        if (correctedDate != selectedDate) {
            selectedDate = correctedDate
        }
        onDateChanged(correctedDate)
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // --- Селектор Дня ---
        WheelPicker(
            items = daysInMonth,
            initialIndex = selectedDate.dayOfMonth - 1,
            onSelectionChanged = { index ->
                val day = index + 1
                if (day <= daysInMonth.size) {
                    selectedDate = selectedDate.withDayOfMonth(day)
                }
            },
            modifier = Modifier.weight(1f)
        )

        // --- Селектор Місяця ---
        WheelPicker(
            items = months,
            initialIndex = selectedDate.monthValue - 1,
            onSelectionChanged = { index ->
                val month = Month.entries[index]
                selectedDate = selectedDate.withMonth(month.value)
            },
            modifier = Modifier.weight(1.5f)
        )

        // --- Селектор Року ---
        WheelPicker(
            items = years,
            initialIndex = years.indexOf(selectedDate.year.toString()),
            onSelectionChanged = { index ->
                val year = years[index].toInt()
                selectedDate = selectedDate.withYear(year)
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WheelPicker(
    items: List<String>,
    initialIndex: Int,
    onSelectionChanged: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
) {
    val pagerState = rememberPagerState(
        initialPage = initialIndex,
        pageCount = { items.size }
    )
//    val snapFlingBehavior = rememberSnapFlingBehavior(pagerState)
    val snapFlingBehavior = PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(10)
    )
    val scope = rememberCoroutineScope()
    // Ефект для виклику onSelectionChanged, коли сторінка стабілізується
    LaunchedEffect(pagerState.currentPage) {
        onSelectionChanged(pagerState.currentPage)
    }

    // Ефект для плавної прокрутки до початкового індексу, якщо він змінився
    LaunchedEffect(initialIndex) {
        scope.launch {
            pagerState.scrollToPage(initialIndex)
        }
    }

    Box(
        modifier = modifier.height(120.dp), // Висота, щоб вмістити 3 елементи
        contentAlignment = Alignment.Center
    ) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                // Ефект згасання для верхнього та нижнього елементів
                .fadingEdge(
                    brush = Brush.verticalGradient(
                        0f to Color.Transparent,
                        0.3f to Color.Black,
                        0.7f to Color.Black,
                        1f to Color.Transparent
                    )
                ),
            flingBehavior = snapFlingBehavior
        ) { pageIndex ->
            val distance = abs(pageIndex - pagerState.currentPage)
            val style = when (distance) {
                0 -> textStyle.copy(color = MaterialTheme.colorScheme.primary) // Центральний елемент
                1 -> textStyle.copy(color = LocalContentColor.current.copy(alpha = 0.6f)) // Сусідні
                else -> textStyle.copy(color = LocalContentColor.current.copy(alpha = 0.4f)) // Дальні
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = items.getOrElse(pageIndex) { "" },
                    style = style,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

// Допоміжна функція-модифікатор для створення ефекту згасання по краях
fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }