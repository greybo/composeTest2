package com.gurman.myapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.gurman.myapplication.feature.coroutin.TestCoroutineScreen
import com.gurman.myapplication.feature.fullscreen.FullSizeScreen
import com.gurman.myapplication.feature.indicator.AnimIndicatorCircleScreen
import com.gurman.myapplication.feature.picker.PickerDateScreen
import com.gurman.myapplication.feature.product.InvoiceProductScreen3
import com.gurman.myapplication.feature.sheet.BottomSheetScreen
import com.gurman.myapplication.feature.swipe.SwipeToRevealScreen5
import com.gurman.myapplication.feature.text.TextFieldPhoneTemplate
import com.gurman.myapplication.feature.topbar.CollapseTopBarScreen
import com.gurman.myapplication.testtest.TestScreen
import com.gurman.myapplication.testtest.TestScreen2
import com.gurman.myapplication.ui.theme.TestAppThemeWrapper

class MainScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        TestAppThemeWrapper {
            Scaffold(
                containerColor = Color.Transparent
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text("Hello test!!!")
                    OutlinedButton(onClick = { navigator.push(TestCoroutineScreen()) }) { Text("Coroutine") }
                    OutlinedButton(onClick = { navigator.push(SwipeToRevealScreen5()) }) { Text("SwipeToRevealScreen5") }
                    OutlinedButton(onClick = { navigator.push(BottomSheetScreen()) }) { Text("BottomSheetScreen") }
                    OutlinedButton(onClick = { navigator.push(InvoiceProductScreen3()) }) { Text("ProductDetailScreen") }
                    OutlinedButton(onClick = { navigator.push(TextFieldPhoneTemplate()) }) { Text("TextFieldPhoneTemplate") }
                    OutlinedButton(onClick = { navigator.push(AnimIndicatorCircleScreen()) }) { Text("IndicatorCircleScreen") }
                    OutlinedButton(onClick = { navigator.push(TestScreen()) }) { Text("Test test") }
                    OutlinedButton(onClick = { navigator.push(TestScreen2()) }) { Text("Test test2") }
                    OutlinedButton(onClick = { navigator.push(CollapseTopBarScreen()) }) { Text("Top app bar") }
                    OutlinedButton(onClick = { navigator.push(PickerDateScreen()) }) { Text("Picker date") }
                    OutlinedButton(onClick = { navigator.push(FullSizeScreen()) }) { Text("FullSizeScreen") }
                }
            }
        }
    }
}