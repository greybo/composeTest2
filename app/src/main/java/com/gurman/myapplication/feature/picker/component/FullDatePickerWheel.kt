package com.gurman.myapplication.feature.picker.component

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate

// getInitialLazyIndex, WheelPicker, getPickerTextStyle - залишаються без змін
// --- 1. ОНОВЛЕНИЙ FullDatePickerWheel ---
// (Тут зосереджена вся нова логіка)

@Composable
fun FullDatePickerWheel(
    modifier: Modifier = Modifier,
    initialDate: LocalDate = LocalDate.now(),
    onDateSelected: (LocalDate) -> Unit,
) {
    // 1. Створюємо списки (роки та місяці - статичні)
    val months = listOf("січ.", "лют.", "бер.", "квіт.", "трав.", "черв.", "лип.", "серп.", "вер.", "жовт.", "лист.", "груд.")
    val years = (1950..2070).map { it.toString() }

    // 2. Початкові індекси для місяця та року
    val initialMonthIndex = initialDate.monthValue - 1
    val initialYearIndex = years.indexOf(initialDate.year.toString())

    val initialMonthLazyIndex = getInitialLazyIndex(months, initialMonthIndex)
    val initialYearLazyIndex = getInitialLazyIndex(years, initialYearIndex)

    // 3. Стани для місяця та року
    val monthState = rememberLazyListState(initialFirstVisibleItemIndex = initialMonthLazyIndex)
    val yearState = rememberLazyListState(initialFirstVisibleItemIndex = initialYearLazyIndex)

    // 4. Слідкуємо за обраними місяцем та роком
    val selectedMonthLazyIndex by remember { derivedStateOf { monthState.firstVisibleItemIndex } }
    val selectedYearLazyIndex by remember { derivedStateOf { yearState.firstVisibleItemIndex } }

    val selectedMonth by remember { derivedStateOf { selectedMonthLazyIndex % months.size } }
    val selectedYear by remember { derivedStateOf { selectedYearLazyIndex % years.size } }

    // 5. --- НОВА ЛОГІКА: Динамічний список днів ---
    val days = remember {
        derivedStateOf {
            val yearInt = years[selectedYear].toInt()
            val monthInt = selectedMonth + 1 // 1-12

            // Обчислюємо максимальний день
            val maxDays = when (monthInt) {
                2 -> if (yearInt % 4 == 0 && (yearInt % 100 != 0 || yearInt % 400 == 0)) 29 else 28
                4, 6, 9, 11 -> 30
                else -> 31
            }
            // Повертаємо новий список днів
            (1..maxDays).map { it.toString().padStart(2, '0') }
        }
    }

    // 6. Початковий індекс для дня (використовує 'days.value')
    val initialDayIndex = remember(initialDate) {
        days.value.indexOf(initialDate.dayOfMonth.toString().padStart(2, '0'))
            .coerceAtLeast(0) // Переконуємось, що індекс не -1
    }

    val initialDayLazyIndex = remember(initialDate) {
        getInitialLazyIndex(days.value, initialDayIndex)
    }

    // 7. Стан для дня
    val dayState = rememberLazyListState(initialFirstVisibleItemIndex = initialDayLazyIndex)
    val selectedDayLazyIndex by remember { derivedStateOf { dayState.firstVisibleItemIndex } }

    // Поточний обраний індекс дня (може бути невалідним, якщо місяць змінився)
    val selectedDay = selectedDayLazyIndex % days.value.size

    // 8. --- НОВА ЛОГІКА: Авто-корекція дня ---
    LaunchedEffect(days.value, selectedDayLazyIndex) {
        // 'selectedDay' - це поточний індекс (напр. 30 для 31-го числа)
        // 'days.value.size' - це нова кількість днів (напр. 28)
        if (selectedDay >= days.value.size) {

            // Новий цільовий індекс - останній день місяця
            val newDayDataIndex = days.value.size - 1 // (напр. 27 для 28-го)

            // Нам потрібно знайти найближчий LazyIndex, що відповідає цьому data-індексу
            val currentLazyIndex = selectedDayLazyIndex
            val currentDataIndex = selectedDay

            // Різниця, на яку треба "відмотати"
            val diff = currentDataIndex - newDayDataIndex
            val newLazyIndexToSnap = currentLazyIndex - diff

            // Анімовано прокручуємо до останнього доступного дня
            dayState.animateScrollToItem(newLazyIndexToSnap)
        }
    }

    // 9. --- ОНОВЛЕНА ЛОГІКА: Виклик onDateSelected ---
    LaunchedEffect(selectedDayLazyIndex, selectedMonth, selectedYear, days.value) {
        // 'selectedDay' тепер завжди буде коректним завдяки LaunchedEffect вище
        val currentDayIndex = selectedDayLazyIndex % days.value.size

        // Перевіряємо, чи індекс в межах нового списку
        if (currentDayIndex < days.value.size) {
            try {
                val dayInt = days.value[currentDayIndex].toInt()
                val monthInt = selectedMonth + 1
                val yearInt = years[selectedYear].toInt()

                onDateSelected(LocalDate.of(yearInt, monthInt, dayInt))
            } catch (e: Exception) {
                // Обробка помилки (наприклад, під час швидкої прокрутки)
            }
        }
    }

    // 10. UI (майже без змін, крім 'items = days.value')
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        WheelPicker(
            items = days.value, // <--- Використовуємо динамічне 'days.value'
            state = dayState,
            modifier = Modifier.width(80.dp)
        )
        WheelPicker(
            items = months,
            state = monthState,
            modifier = Modifier.width(100.dp)
        )
        WheelPicker(
            items = years,
            state = yearState,
            modifier = Modifier.width(100.dp)
        )
    }
}

// Допоміжна функція для обчислення початкового "безкінечного" індексу
private fun getInitialLazyIndex(
    items: List<*>,
    initialDataIndex: Int,
): Int {
    // Починаємо з середини "безкінечного" списку
    val middle = Int.MAX_VALUE / 2
    // Обчислюємо залишок, щоб знати, де починається "цикл"
    val remainder = middle % items.size
    // Віднімаємо залишок і додаємо наш цільовий індекс.
    // Це гарантує, що (initialLazyIndex % items.size) == initialDataIndex
    return middle - remainder + initialDataIndex
}

// --- 2. WheelPicker (БЕЗ ЗМІН) ---
// (Залишається той самий, що й у попередній відповіді)
@Composable
private fun WheelPicker(
    modifier: Modifier = Modifier,
    items: List<String>,
    state: LazyListState,
    itemHeight: Dp = 60.dp,
    visibleItemsCount: Int = 3,
) {
    val pickerHeight = itemHeight * visibleItemsCount
    val verticalPadding = (pickerHeight - itemHeight) / 2

    val flingBehavior = rememberSnapFlingBehavior(lazyListState = state)

    Box(
        modifier = modifier.height(pickerHeight),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            state = state,
            contentPadding = PaddingValues(vertical = verticalPadding),
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(count = Int.MAX_VALUE) { lazyIndex ->

                val dataIndex = lazyIndex % items.size

                // Перевірка, чи список 'items' не порожній
                if (dataIndex in items.indices) {
                    val item = items[dataIndex]
                    Box(
                        modifier = Modifier.height(itemHeight),
                        contentAlignment = Alignment.Center
                    ) {
                        val isSelected = (lazyIndex == state.firstVisibleItemIndex)
                        Text(
                            text = item,
                            style = getPickerTextStyle(isSelected = isSelected)
                        )
                    }
                }
            }
        }

        // ... Декоративні лінії ...
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Divider(color = Color.Gray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(itemHeight - 2.dp))
            Divider(color = Color.Gray, thickness = 1.dp)
        }
    }
}

// Функція для стилізації тексту
@Composable
private fun getPickerTextStyle(isSelected: Boolean): TextStyle {
    return if (isSelected) {
        // Стиль для обраного (центрального) елемента
        TextStyle(
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    } else {
        // Стиль для інших (неактивних) елементів
        TextStyle(
            color = Color.Gray,
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal
        )
    }
}
// --- Приклад використання ---
@PreviewLightDark
@Composable
fun DatePickerScreenPreview() {
    var selectedDate by remember { mutableStateOf(LocalDate.of(2054, 2, 1)) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black // Чорний фон, як на зображенні
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            FullDatePickerWheel(
                initialDate = selectedDate,
                onDateSelected = { newDate ->
                    selectedDate = newDate
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Обрана дата: $selectedDate",
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}