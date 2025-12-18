package com.gurman.myapplication.feature.picker.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gurman.myapplication.ui.theme.PreviewThemeWrapper
import kotlinx.coroutines.flow.distinctUntilChanged
import java.io.Serializable
import java.text.DateFormatSymbols
import java.util.Calendar
import kotlin.math.abs

/**
 * Допоміжна функція для отримання кількості днів у конкретному місяці та році.
 * Вона автоматично враховує високосні роки (вимога №4).
 */
fun getDaysInMonth(year: Int, month: Int): Int {
    val calendar = Calendar.getInstance()
    // Місяці в Calendar 0-індексовані (0 = січень, 1 = лютий і т.д.)
    calendar.set(year, month, 1)
    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
}

/**
 * Головний Composable, що об'єднує три селектори: День, Місяць, Рік.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DateWheelPicker(
    modifier: Modifier = Modifier,
    onDateSelected: (day: Int, month: Int, year: Int) -> Unit,
) {
    val calendar = Calendar.getInstance()

    // Початкові значення
    val initialYear = calendar.get(Calendar.YEAR)
    val initialMonth = calendar.get(Calendar.MONTH) // 0-indexed
    val initialDay = calendar.get(Calendar.DAY_OF_MONTH) // 1-indexed

    // Списки даних
    val years = remember { (1900..2100).map { it.toString() } }
    val months = remember { DateFormatSymbols().months.filter { it.isNotEmpty() } }

    // Стан (State) для обраних значень
    var selectedYear by remember { mutableStateOf(initialYear) }
    var selectedMonth by remember { mutableStateOf(initialMonth) } // 0-indexed

    // Динамічний список днів, що залежить від місяця та року
    val daysInMonth = remember(selectedYear, selectedMonth) {
        getDaysInMonth(selectedYear, selectedMonth)
    }
    val days = remember(daysInMonth) { (1..daysInMonth).map { it.toString() } }

    // Стан для дня. Починаємо з 1-індексованого значення.
    var selectedDay by remember { mutableStateOf(initialDay) }

    // Вимога №4: Коригування дня, якщо він стає недійсним (наприклад, 31 лютого)
    LaunchedEffect(daysInMonth) {
        if (selectedDay > daysInMonth) {
            selectedDay = daysInMonth
        }
    }

    // Викликаємо callback при зміні будь-якої частини дати
    LaunchedEffect(selectedDay, selectedMonth, selectedYear) {
        onDateSelected(selectedDay, selectedMonth, selectedYear)
    }

    // Висота одного елемента
    val itemHeight = 48.dp

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // --- Селектор Дня ---
        WheelPicker(
            items = days,
            // selectedDay 1-індексований, список 0-індексований
            selectedIndex = selectedDay - 1,
            onSelectedIndexChange = { index ->
                selectedDay = index + 1
            },
            modifier = Modifier.width(80.dp),
            itemHeight = itemHeight
        )

        // --- Селектор Місяця ---
        WheelPicker(
            items = months,
            selectedIndex = selectedMonth,
            onSelectedIndexChange = { index ->
                selectedMonth = index
            },
            modifier = Modifier.width(120.dp),
            itemHeight = itemHeight
        )

        // --- Селектор Року ---
        WheelPicker(
            items = years,
            selectedIndex = years.indexOf(selectedYear.toString()),
            onSelectedIndexChange = { index ->
                selectedYear = years[index].toInt()
            },
            modifier = Modifier.width(100.dp),
            itemHeight = itemHeight
        )
    }
}

/**
 * Універсальний "барабанний" селектор (Вимога №1: Використовує VerticalPager)
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WheelPicker(
    modifier: Modifier = Modifier,
    items: List<String>,
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    itemHeight: Dp = 48.dp,
) {
    val pageCount = Int.MAX_VALUE
    val itemsSize = items.size

    // Початкова сторінка: центруємо "нескінченний" список на початковому індексі
    val initialPage = (pageCount / 2) - (pageCount / 2 % itemsSize) + selectedIndex

    val pagerState = rememberPagerState(initialPage = initialPage) { pageCount }

    // Оновлюємо callback, щоб уникнути зайвих рекомпозицій
    val currentOnSelectedIndexChange by rememberUpdatedState(onSelectedIndexChange)

    // Ефект для сповіщення про зміну сторінки ПІСЛЯ зупинки прокрутки
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.isScrollInProgress }
            .distinctUntilChanged()
            .collect { isScrolling ->
                if (!isScrolling) {
                    val newIndex = pagerState.currentPage % itemsSize
                    if (newIndex != selectedIndex) {
                        currentOnSelectedIndexChange(newIndex)
                    }
                }
            }
    }

    // Ефект для "примусового" прокручування, якщо індекс змінився ззовні
    // (наприклад, зміна місяця з 31 днем на місяць з 28 днями)
    LaunchedEffect(selectedIndex, itemsSize) {
        val targetPage = (pageCount / 2) - (pageCount / 2 % itemsSize) + selectedIndex
        if (pagerState.currentPage != targetPage) {
            // Використовуємо scrollToPage для миттєвого "перестрибування"
            pagerState.scrollToPage(targetPage)
        }
    }

    // Вимога №3: показ 3 елементів (попередній, поточний, наступний)
    // Ми досягаємо цього, встановлюючи висоту Box = itemHeight * 3
    Box(
        modifier = modifier.height(itemHeight * 3),
        contentAlignment = Alignment.Center
    ) {
        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->

            val index = page % itemsSize
            val itemText = items.getOrNull(index) ?: ""

            // Розрахунок "відстані" від центру для стилізації
            // 0 = в центрі, 1 = на 1 позицію вище/нижче, і т.д.
            val offset = abs(page - pagerState.currentPage)

            // Вимога №3: виділення центрального елемента
            val (style, color, alpha, scale) = when (offset) {
                0 -> Quaternary(MaterialTheme.typography.titleLarge, MaterialTheme.colorScheme.primary, 1f, 1f) // Вибраний
                1 -> Quaternary(MaterialTheme.typography.bodyMedium, MaterialTheme.colorScheme.onSurface, 0.6f, 0.8f) // Сусідні
                else -> Quaternary(MaterialTheme.typography.bodySmall, MaterialTheme.colorScheme.onSurface, 0.4f, 0.6f) // Далекі
            }

            Box(
                modifier = Modifier
                    .height(itemHeight)
                    .fillMaxWidth()
                    // Плавна анімація прозорості та розміру
                    .graphicsLayer {
                        this.alpha = alpha
                        this.scaleX = scale
                        this.scaleY = scale
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = itemText,
                    style = style,
                    color = color,
                    textAlign = TextAlign.Center,
                    maxLines = 1
                )
            }
        }

        // Декоративна "рамка" для виділення центрального елемента
        Box(
            modifier = Modifier
                .height(itemHeight)
                .fillMaxWidth()
                .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
        )
    }
}

data class Quaternary<out A, out B, out C, out D>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
) : Serializable {

    /**
     * Returns string representation of the [Triple] including its [first], [second] and [third] values.
     */
    override fun toString(): String = "($first, $second, $third, $fourth)"
}
@PreviewLightDark
@Composable
fun MyScreen2() {
    PreviewThemeWrapper {
        var selectedDateText by remember { mutableStateOf("Оберіть дату") }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = selectedDateText,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            DateWheelPicker(
                onDateSelected = { day, month, year ->
                    // 'month' тут 0-індексований (0=Січень), тому додаємо 1 для показу
                    selectedDateText = "$day/${month + 1}/$year"
                }
            )
        }
    }

}