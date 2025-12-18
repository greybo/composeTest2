package com.gurman.myapplication.feature.indicator

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.min

@Preview
@Composable
fun CircularStatusIndicator(
    modifier: Modifier = Modifier,
    currentCredits: Int = 50,
    requiredCredits: Int = 80,
    indicatorSize: Dp = 220.dp,
    indicatorThickness: Dp = 16.dp,
    backgroundColor: Color = Color.LightGray.copy(alpha = 0.2f),
    foregroundColor: Color = Color(0xFF4CAF50),
    animationDuration: Int = 1000,
    gapAngle: Float = 60f // Кут розриву в градусах
) {
    val progress = remember { Animatable(0f) }
    
    LaunchedEffect(currentCredits) {
        progress.animateTo(
            targetValue = currentCredits.toFloat() / requiredCredits.toFloat(),
            animationSpec = tween(durationMillis = animationDuration)
        )
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(indicatorSize)
        ) {
            // Круговий індикатор з невеликим розривом
            Canvas(
                modifier = Modifier.size(indicatorSize)
            ) {
                val strokeWidth = indicatorThickness.toPx()
                val diameterOffset = strokeWidth / 2
                val arcDiameter = min(size.width, size.height) - diameterOffset
                val arcSize = Size(arcDiameter, arcDiameter)

                // Фонова дуга (майже повне коло)
                drawArc(
                    color = backgroundColor,
                    startAngle = 90f + gapAngle/2,
                    sweepAngle = 360f - gapAngle,
                    useCenter = false,
                    size = arcSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )

                // Прогрес дуги
                drawArc(
                    color = foregroundColor,
                    startAngle = 90f + gapAngle/2,
                    sweepAngle = (360f - gapAngle) * progress.value,
                    useCenter = false,
                    size = arcSize,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
            }

            // Текст всередині індикатора
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$requiredCredits",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "required for Platinum",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
            }
        }

        // Текст під індикатором
        Text(
            text = "$currentCredits Status Credits",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = foregroundColor,
            modifier = Modifier.padding(top = 8.dp)
        )
        
        Text(
            text = "Your balance 😊",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCircularStatusIndicator() {
    CircularStatusIndicator(
        currentCredits = 20,
        requiredCredits = 80,
        foregroundColor = Color(0xFF6200EE),
        gapAngle = 30f // Регулюйте цей параметр для зміни розміру розриву
    )
}