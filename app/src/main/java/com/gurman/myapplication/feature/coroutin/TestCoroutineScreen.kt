package com.gurman.myapplication.feature.coroutin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class TestCoroutineScreen : Screen {
    private val mutex = Mutex()

    private val foo1 = Foo()
    private val fooCopy = foo1
    private var fooCopy2: Foo? = null

    init {
        fooCopy.text = "2"
        setFoo(fooCopy)
        fooCopy2?.text = "3"
        foo1.text = "1"
    }

    fun setFoo(fooCopy: Foo) {
        fooCopy2 = fooCopy
    }

    @Composable
    override fun Content() {

        var testValue by remember { mutableStateOf("") }
        var testValue2 by remember { mutableStateOf("") }
        var testValue3 by remember { mutableStateOf("") }

        Column(modifier = Modifier.fillMaxSize()) {
            Text("Test coroutine")
            HorizontalDivider(color = Color.Red, thickness = 2.dp)

            Button(onClick = { testValue = test1() }) { Text("run async") }

            Text("Compare: loop=100_000, $testValue")

            Button(onClick = { testValue2 = test2() }) { Text("run mutex") }

            Text("Compare: loop=100_000, $testValue2")

            Button(onClick = { testValue3 = test3() }) { Text("run supervisorScope") }

            Text("Compare: loop=100_000, $testValue3")
            //==========================================================
            Text(
                "" +
                        "foo text=${foo1.text}, " +
                        "\nfooCopy text=${fooCopy.text}," +
                        "\nfooCopy2 text=${fooCopy2?.text}",
                modifier = Modifier.padding(top = 24.dp)
            )
        }
    }

    fun test1() = runBlocking {
        var value = 0
        val timestamp = System.currentTimeMillis()
        Array(100_000) {
            async(Dispatchers.Default) {
                ++value
            }
        }.forEach { it.await() }

        "\nresult=$value, time=${System.currentTimeMillis() - timestamp}"
    }

    fun test2() = runBlocking {
        var value = 0
        val timestamp = System.currentTimeMillis()
        Array(100_000) {
            mutex.withLock {
                ++value
            }
        }

        "\nresult=$value, time=${System.currentTimeMillis() - timestamp}"
    }

    fun test3() = runBlocking {
        var value = 0
        val timestamp = System.currentTimeMillis()
        Array(100_000) {
            supervisorScope {
                ++value
            }
        }
        "\nresult=$value, time=${System.currentTimeMillis() - timestamp}"
    }
}

data class Foo(var text: String = "")