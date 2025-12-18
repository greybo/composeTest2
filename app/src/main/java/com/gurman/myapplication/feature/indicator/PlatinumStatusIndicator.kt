@file:Suppress("UNREACHABLE_CODE")

package com.gurman.myapplication.feature.indicator

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import com.gurman.myapplication.R
import kotlinx.coroutines.launch


@Composable
fun PlatinumStatusIndicator(
    valueIn1: Int = 80,
    valueOut1: Int = 150,
    valueIn2: Int = 130,
    valueOut2: Int = 150,
    indicatorSize: Dp = 150.dp,
    indicatorThickness: Dp = 15.dp,
    backgroundColor: Color = Color.LightGray.copy(alpha = 1f),
    foregroundColor1: Color = Color(0xFF4CAF50),
    foregroundColor2: Color = Color(0xFFAF4C77),
    animationDuration: Int = 1000,
    bitmap1: ImageBitmap = ImageBitmap.imageResource(id = R.drawable.icons8_more_64),
    bitmap2: ImageBitmap = ImageBitmap.imageResource(id = R.drawable.icons8_more_64),
    imagePadding: Int = 40,
    insideOffset: Int = 20,
) {

    val scope = rememberCoroutineScope()
    val progress = remember { Animatable(0f) }
    val progress2 = remember { Animatable(0f) }

    val sweetAngle21 = if (progress2.value > 0.75) 270f else 360f * progress2.value
    val sweetAngle22 = if (progress2.value < 0.75) 0f else 360f * progress2.value - 270f
    fun progressAnim() {
        scope.launch {
            progress.snapTo(0f)
            progress.animateTo(
                targetValue = valueIn1.toFloat() / valueOut1.toFloat(),
                animationSpec = tween(durationMillis = animationDuration)
            )
        }
        scope.launch {
            progress2.snapTo(0f)
            progress2.animateTo(
                targetValue = valueIn2.toFloat() / valueOut2.toFloat(),
                animationSpec = tween(durationMillis = animationDuration)
            )
        }
    }
    LaunchedEffect(Unit) {
        progressAnim()
    }

    Box(
        modifier = Modifier.clickable { progressAnim() }
    ) {

        Canvas(
            modifier = Modifier.size(height = indicatorSize, width = indicatorSize * 2)
        ) {
            val strokeWidth = indicatorThickness.toPx()
            val diameterOffset = strokeWidth / 2
            val arcDiameter = size.height - diameterOffset

            val arcSize = Size(arcDiameter - diameterOffset, arcDiameter - diameterOffset)

            val offset1 = Offset(
                diameterOffset,
                diameterOffset
            )
            val offset2 = Offset(
                arcDiameter - strokeWidth - insideOffset,// diameterOffset ,
                diameterOffset
            )
            drawImage(
                image = bitmap2,
                dstOffset = offset2.round().plus(IntOffset(imagePadding / 2, imagePadding / 2)),
                dstSize = IntSize(
                    width = size.height.toInt() - indicatorThickness.toPx().toInt() - imagePadding,
                    height = size.height.toInt() - indicatorThickness.toPx().toInt() - imagePadding
                ),
                filterQuality = FilterQuality.High,
                colorFilter = ColorFilter.tint(color = Color.Black),
            )
            // bg 2-1
            drawArc(
                color = backgroundColor,
                startAngle = 270f,
                sweepAngle = 270f,
                useCenter = false,
                topLeft = offset2,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
            )
            // Progress 2-1
            drawArc(
                color = foregroundColor2,
                startAngle = 270f,
                sweepAngle = sweetAngle21,
                useCenter = false,
                topLeft = offset2,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
            )
            // bg 1
            drawArc(
                color = backgroundColor,
                startAngle = 270f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = offset1,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
            )

            // Progress 1
            drawArc(
                color = foregroundColor1,
                startAngle = 270f,
                sweepAngle = 360f * progress.value,
                useCenter = false,
                topLeft = offset1,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
            )
//             bg 2-2
            drawArc(
                color = backgroundColor,
                startAngle = 180f,
                sweepAngle = 90f,
                useCenter = false,
                topLeft = offset2,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
            )
//             Progress 2-2
            drawArc(
                color = foregroundColor2,
                startAngle = 180f,
                sweepAngle = sweetAngle22,
                useCenter = false,
                topLeft = offset2,
                size = arcSize,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
            )

            drawImage(
                image = bitmap1,
                dstOffset = offset1.round().plus(IntOffset(imagePadding / 2, imagePadding / 2)),
                dstSize = IntSize(
                    width = size.height.toInt() - indicatorThickness.toPx().toInt() - imagePadding,
                    height = size.height.toInt() - indicatorThickness.toPx().toInt() - imagePadding
                ),
                filterQuality = FilterQuality.High,
                colorFilter = ColorFilter.tint(color = Color.Black),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPlatinumStatusIndicator() {
    PlatinumStatusIndicator()
}