package com.gurman.myapplication.feature.picker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.gurman.myapplication.feature.picker.DataPickerTexts.daysList
import com.gurman.myapplication.feature.picker.DataPickerTexts.monthsMock
import com.gurman.myapplication.feature.picker.DataPickerTexts.yearList
import com.gurman.myapplication.ui.theme.AppScreenPreview
import com.gurman.myapplication.ui.theme.ThemePreviews
import timber.log.Timber
import java.time.LocalDate

@ThemePreviews
@Composable
private fun PrivacyScreen() = AppScreenPreview(PickerDateScreen())

class PickerDateScreen : Screen {
    @Composable
    override fun Content() {
        val localDate: LocalDate = LocalDate.now()

        val day = remember { mutableIntStateOf(localDate.dayOfMonth) }
        val month = remember { mutableIntStateOf(localDate.monthValue) }
        val year = remember { mutableIntStateOf(localDate.year) }

        Column {
            Row(
                modifier = Modifier
                    .padding(start = 50.dp)
                    .fillMaxWidth(),
            ) {

                val countDays = localDate
                    .withMonth(month.intValue)
                    .withYear(year.intValue)
                    .lengthOfMonth()
                val daysList = daysList(countDays)
                Timber.w("countDays ${daysList.size}, day = ${day.intValue}, moth = ${month.intValue}, year = ${year.intValue}")
                WheelPickerCell(daysList, selected = day.intValue.toString()) { dayIt ->
                    Timber.w("Dey $dayIt")
                    day.intValue = daysList.indexOf(dayIt).let { if (it == -1) 0 else it }
                }

                WheelPickerCell(monthsMock, selected = monthsMock.getOrNull(month.intValue) ?: "") {
                    val monthIndex = monthsMock.indexOf(it)
                    Timber.w("Month = $it, index = $monthIndex")
                    month.intValue = if (monthIndex == -1) 1 else monthIndex+1
                }

                WheelPickerCell(yearList, selected = localDate.year.toString()) {
                    year.intValue = it.toIntOrNull() ?: 0
                    Timber.w("Years = $it")
                }
            }
        }
    }
}

object DataPickerTexts {
    fun daysList(countDays: Int = 31): List<String> {
        val list = mutableListOf<String>()
        for (i in 1..countDays) {
            list.add(i.toString())
        }
        return list
    }

    val monthsMock = listOf("січ.", "лют.", "бер.", "квіт.", "трав.", "черв.", "лип.", "серп.", "вер.", "жовт.", "лист.", "груд.")
    val yearList = mutableListOf<String>()

    init {
        for (i in 1900..2100) {
            yearList.add((i).toString())
        }
    }
}

