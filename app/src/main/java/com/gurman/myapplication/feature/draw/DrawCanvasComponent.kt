package com.gurman.myapplication.feature.draw

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.gurman.myapplication.R
import timber.log.Timber

@SuppressLint("SuspiciousIndentation")
@Composable
fun DrawCanvasComponent() {
    val context = LocalContext.current
    val density = LocalDensity.current // OR, for example, Density(1f, 1f)
    val direction = LocalLayoutDirection.current // OR, for example, LayoutDirection.Ltr

    val myPainter = painterResource(id = R.drawable.rectangle_dotted_blank)
    val size = myPainter.intrinsicSize
    val myImageBitmap = myPainter.toImageBitmap(size, density, direction)

    val imageBitmap = ImageBitmap.imageResource(id = R.drawable.rectangle_dotted_blank)

//    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .cutOutOutlineImage(
                strokeColor = Color.Green,
                outlineColor = Color.Black.copy(alpha = 0.7f),
                paddingHorizontal = 16.dp,
                bitmap = imageBitmap
            )
            .onSizeChanged {
                Timber.d("OnSizeChanged: ${it.height}")
            },

        color = Color.Transparent
    ) {
//        Icon(imageBitmap, contentDescription = "", modifier = Modifier.padding(horizontal = 24.dp))
    }
//    }
}


@Preview
@Composable
fun PreviewDrawCanvasComponent() {
    DrawCanvasComponent()
}

fun Painter.toImageBitmap(
    size: Size,
    density: Density,
    layoutDirection: LayoutDirection,
): ImageBitmap {
    val bitmap = ImageBitmap(size.width.toInt(), size.height.toInt())
    val canvas = Canvas(bitmap)
    CanvasDrawScope().draw(density, layoutDirection, canvas, size) {
        draw(size, colorFilter = ColorFilter.tint(color = Color.Red))
    }
    return bitmap
}