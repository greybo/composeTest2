package com.gurman.myapplication.testtest

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

class TestScreen : Screen {
    private val list = listOf("a1-01-01", "b1-01-01", "a2-03-03", "e1", "b2-02-03")
    val regexPlace = "^[a-zA-Zа-яА-Я0-9]+\$".toRegex()

    @Composable
    override fun Content() {

        Scaffold { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                Text("Text screen!!")
                val list2 = list.map {
                    reversedFirst(it)
                }
                list2.sorted().map { code ->
                    Text(code)
                }
            }
        }
    }

    private fun reversedFirst(code: String): String {
        val array = code.split("-")
        return if (array.size == 1) {
            code
        } else {
            var result = ""
            array.mapIndexed { index, s ->
                result += when (index) {
                    0 -> s.reversed()
                    else -> "-$s"
                }
            }
            result
        }
    }
}