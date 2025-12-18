package com.gurman.myapplication.feature.select

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun DialogMarkCompose() {
    Dialog(onDismissRequest = {}) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .background(color = Color.White)
        ) {
            ColorPickerEnum.entries.map {
                Text(
                    text = "Dialog maker",
                    color = it.color,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp)
                )
            }
        }
    }
}

enum class ColorPickerEnum(val color: Color) {
    Blue(Color.Blue),
    Green(Color.Green),
    Red(Color.Red),
    Cyan(Color.Cyan),
    Magenta(Color.Magenta),
    Yellow(Color(0xFFFFC107)),
    Orange(Color(0xFFFF5722)),
}

val LightRed = Color(0xFFE91E63)
val LightGray = Color(0xFF2196F3)
val LighterGray = Color(0xFF673AB7)
val MediumGray = Color(0xFF8BC34A)
val DarkerGray = Color(0xFFCDDC39)
val VeryDarkGray = Color(0xFFFFC107)
val BlackGray = Color(0xFFFF5722)