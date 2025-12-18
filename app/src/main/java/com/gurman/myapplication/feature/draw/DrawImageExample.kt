package com.gurman.myapplication.feature.draw

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.gurman.myapplication.R

@Composable
fun DrawImageExample() {
    var srcOffsetX by remember { mutableIntStateOf(0) }
    var srcOffsetY by remember { mutableIntStateOf(0) }
    var srcWidth by remember { mutableIntStateOf(1080) }
    var srcHeight by remember { mutableIntStateOf(1080) }

    var dstOffsetX by remember { mutableIntStateOf(0) }
    var dstOffsetY by remember { mutableIntStateOf(0) }
    var dstWidth by remember { mutableIntStateOf(1080) }
    var dstHeight by remember { mutableIntStateOf(1080) }

    val density = LocalDensity.current // OR, for example, Density(1f, 1f)
    val direction = LocalLayoutDirection.current // OR, for example, LayoutDirection.Ltr

    val myPainter = painterResource(id = R.drawable.rectangle_dotted_blank)
    val size = myPainter.intrinsicSize

    val bitmap = myPainter.toImageBitmap(size, density, direction)
//    val bitmap = ImageBitmap.imageResource(id = R.drawable.rectangle_dotted_blank)

    Column {
//        Canvas(modifier = canvasModifier) {
//            drawImage(bitmap)
//        }

        Spacer(modifier = Modifier.height(10.dp))
//    TutorialText2(text = "Src, Dst Offset and Size")
        Canvas(modifier = canvasModifier) {
            drawImage(
                image = bitmap,
                srcOffset = IntOffset(srcOffsetX, srcOffsetY),
                srcSize = IntSize(srcWidth, srcHeight),
                dstOffset = IntOffset(dstOffsetX, dstOffsetY),
                dstSize = IntSize(dstWidth, dstHeight),
                filterQuality = FilterQuality.High
            )
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = "srcOffsetX $srcOffsetX")
            Slider(
                value = srcOffsetX.toFloat(),
                onValueChange = { srcOffsetX = it.toInt() },
                valueRange = -540f..540f,
            )

            Text(text = "srcOffsetY $srcOffsetY")
            Slider(
                value = srcOffsetY.toFloat(),
                onValueChange = { srcOffsetY = it.toInt() },
                valueRange = -540f..540f,
            )
            Text(text = "srcWidth $srcWidth")
            Slider(
                value = srcWidth.toFloat(),
                onValueChange = { srcWidth = it.toInt() },
                valueRange = 0f..1080f,
            )

            Text(text = "srcHeight $srcHeight")
            Slider(
                value = srcHeight.toFloat(),
                onValueChange = { srcHeight = it.toInt() },
                valueRange = 0f..1080f,
            )


            Text(text = "dstOffsetX $dstOffsetX")
            Slider(
                value = dstOffsetX.toFloat(),
                onValueChange = { dstOffsetX = it.toInt() },
                valueRange = -540f..540f,
            )

            Text(text = "dstOffsetY $dstOffsetY")
            Slider(
                value = dstOffsetY.toFloat(),
                onValueChange = { dstOffsetY = it.toInt() },
                valueRange = -540f..540f,
            )
            Text(text = "dstWidth $dstWidth")
            Slider(
                value = dstWidth.toFloat(),
                onValueChange = { dstWidth = it.toInt() },
                valueRange = 0f..1080f,
            )

            Text(text = "dstHeight $dstHeight")
            Slider(
                value = dstHeight.toFloat(),
                onValueChange = { dstHeight = it.toInt() },
                valueRange = 0f..1080f,
            )
        }
    }
}

private val canvasModifier = Modifier
//    .padding(8.dp)
    .shadow(10.dp)
    .background(Color.White)
    .fillMaxWidth()
    .height(250.dp)