package com.gurman.myapplication.feature.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.cutOutOutlineBtn(
    strokeColor: Color = Color.Red,
    outlineColor: Color = Color.Green.copy(alpha = 0.5f),
    aspectRatio: Float = 1.5f,
    strokeWidth: Dp = 2.dp,
    radius: Float = 30F,
    cap: StrokeCap = StrokeCap.Round
) = this.drawWithContent {
    val outlinePath = Path()
    outlinePath.addRect(Rect(Offset(0f, 0f), size))

    val cutoutHeight = size.height * 0.9f
    val cutoutWidth = cutoutHeight * aspectRatio
    val center = Offset(size.width / 2f, size.height / 2f)

    val cutoutPath = Path()
    val roundRect = RoundRect(
        Rect(
            topLeft = center - Offset(
                cutoutWidth / 2f,
                cutoutHeight / 2f
            ),
            bottomRight = center + Offset(
                cutoutWidth / 2f,
                cutoutHeight / 2f
            )
        ),
        cornerRadius = CornerRadius(radius, radius)
    )
    cutoutPath.addRoundRect(roundRect)

    val finalPath = Path.combine(
        PathOperation.Difference,
        outlinePath,
        cutoutPath
    )

    val dashedStroke = Stroke(
        cap = cap,
        width = strokeWidth.toPx(),
        pathEffect = PathEffect.dashPathEffect(
            intervals = floatArrayOf(0f, 0f)
        )
    )

    drawContent()

    drawOutline(
        outline = Outline.Generic(finalPath),
        color = outlineColor
    )

    drawOutline(
        outline = Outline.Rounded(roundRect),
        style = dashedStroke,
        brush = SolidColor(strokeColor)
    )
}