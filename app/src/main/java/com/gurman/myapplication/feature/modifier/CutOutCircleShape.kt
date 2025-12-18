package com.gurman.myapplication.feature.modifier

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
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
import kotlin.math.min

fun Modifier.cutOutCircleOutline(
    strokeColor: Color = Color.Red,
    outlineColor: Color =  Color.Green.copy(alpha = 0.5f),
    aspectRation: Float = 0.8f,
    strokeWidth: Dp = 2.dp,
    dashLength: Dp = 6.dp,
    gapLength: Dp = 4.dp,
    cap: StrokeCap = StrokeCap.Square
) = this.drawWithContent {
    val outlinePath = Path()
    outlinePath.addRect(Rect(Offset(0f, 0f), size))

    val cutoutRadius = min(size.width, size.height) * aspectRation
    val cutoutWidth = cutoutRadius
    val cutoutHeight = cutoutRadius
    val center = Offset(size.width / 2f, size.height / 2f)

    val cutoutPath = Path()
    val roundRect = Rect(
        topLeft = center - Offset(
            cutoutWidth / 2f,
            cutoutHeight / 2f
        ),
        bottomRight = center + Offset(
            cutoutWidth / 2f,
            cutoutHeight / 2f
        )
    )
    cutoutPath.addOval(oval = roundRect)


    val radius = size.width * (aspectRation - 0.07f)
    val cutoutPath2 = Path()
    val roundRect2 = Rect(
        topLeft = center - Offset(
            radius / 2f,
            radius / 2f
        ),
        bottomRight = center + Offset(
            radius / 2f,
            radius / 2f
        )
    )
    cutoutPath2.addOval(oval = roundRect2)

    val finalPath = Path.combine(
        operation = PathOperation.Difference,
        path1 = outlinePath,
        path2 = cutoutPath2
    )

//    val finalPath = Path.combine(
//        PathOperation.Difference,
//        outlinePath,
//        cutoutPath
//    )

    val dashedStroke = Stroke(
        cap = cap,
        width = strokeWidth.toPx(),
        pathEffect = PathEffect.dashPathEffect(
            intervals = floatArrayOf(dashLength.toPx(), gapLength.toPx()),
        )
    )

    drawContent()

    drawOutline(
        outline = Outline.Generic(finalPath),
        color = outlineColor
    )

    drawOutline(
        outline = Outline.Generic(cutoutPath),
        style = dashedStroke,
        brush = SolidColor(strokeColor)
    )
}
