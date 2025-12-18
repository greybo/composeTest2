package com.gurman.myapplication.feature.product.components

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.rememberAsyncImagePainter
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import coil3.request.ImageRequest
import coil3.size.Size
import com.gurman.myapplication.R

@Composable
fun GifImage(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(AnimatedImageDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()

    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(context).data(data = R.drawable.icons8_done).apply(block = {
            size(Size.ORIGINAL)
        }).build(), imageLoader = imageLoader
    )
    Image(painter, contentDescription = null, modifier = Modifier.size(24.dp))
}
