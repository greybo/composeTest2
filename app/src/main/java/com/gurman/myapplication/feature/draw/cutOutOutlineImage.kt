package com.gurman.myapplication.feature.draw

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

fun Modifier.cutOutOutlineImage(
    strokeColor: Color,
    outlineColor: Color = Color.Transparent,
    paddingHorizontal: Dp = 16.dp,
    bitmap: ImageBitmap
) = this.drawWithContent {

    val strokeWidth: Dp = 5.dp

    val outlinePath = Path()
    outlinePath.addRect(Rect(Offset(0f, 0f), size))

    val bitmapWidth = bitmap.width - paddingHorizontal.toPx().toInt() * 2
    val bitmapHeight = bitmap.height

    val cutoutTopLeft =
        Offset(
            size.width / 2f - bitmapWidth / 2 + strokeWidth.toPx(),
            size.height / 2f - bitmapHeight / 2 + strokeWidth.toPx()
        )
    val cutoutBottomRight =
        Offset(
            size.width / 2f + bitmapWidth / 2 - strokeWidth.toPx(),
            size.height / 2f + bitmapHeight / 2 - strokeWidth.toPx()
        )

    val cutoutPath = Path()
    val roundRect = RoundRect(
        Rect(
            topLeft = cutoutTopLeft,
            bottomRight = cutoutBottomRight
        ),
    )
    cutoutPath.addRoundRect(roundRect)

    val finalPath = Path.combine(
        PathOperation.Difference,
        outlinePath,
        cutoutPath
    )

    drawContent()

    drawOutline(
        outline = Outline.Generic(finalPath),
        color = outlineColor
    )

    drawImage(
        image = bitmap,
        dstOffset = IntOffset((size.width / 2).toInt() - bitmapWidth / 2, (size.height / 2).toInt() - bitmapHeight / 2),
        dstSize = IntSize(bitmapWidth, bitmapHeight),
        filterQuality = FilterQuality.High,
        colorFilter = ColorFilter.tint(color = strokeColor),
    )
}
